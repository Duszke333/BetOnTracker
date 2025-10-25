import boto3
import os
import time
import json

from dotenv import load_dotenv
from botocore.auth import SigV4Auth
from botocore.awsrequest import AWSRequest
from botocore.exceptions import ClientError
import requests

load_dotenv()

from graph import runnable


sqs_in = boto3.resource('sqs',
    endpoint_url=os.environ.get('SQS_URL_RAW'),
    aws_access_key_id=os.environ.get('SQS_ADMIN_ACCESS_KEY_ID'),
    aws_secret_access_key=os.environ.get('SQS_ADMIN_SECRET_KEY'),
    region_name='fr-par'
)

queue_in = sqs_in.get_queue_by_name(QueueName='queue-raw-articles')

sqs_out = boto3.resource('sqs',
    endpoint_url=os.environ.get('SQS_URL_SUMMARIES'),
    aws_access_key_id=os.environ.get('SQS_ADMIN_ACCESS_KEY_ID'),
    aws_secret_access_key=os.environ.get('SQS_ADMIN_SECRET_KEY'),
    region_name='fr-par'
)

queue_out = sqs_out.get_queue_by_name(QueueName='queue-summaries')


def download_from_s3(path):
    bucket_name = 'hackathon-team-5-pl'
    REGION = 'pl-waw'

    url = f'https://{bucket_name}.s3.{REGION}.scw.cloud/{path}'

    request = AWSRequest(method='GET', url=url, data=b'')
    credentials = boto3.Session(
        aws_access_key_id=os.getenv('AWS_ACCESS_KEY_ID'),
        aws_secret_access_key=os.getenv('AWS_SECRET_ACCESS_KEY')
    ).get_credentials()

    SigV4Auth(credentials, 's3', REGION).add_auth(request)

    request.headers['X-Amz-Content-Sha256'] = 'e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855'  # SHA256 hash of an empty string

    # Make the request
    response = requests.get(url, headers=dict(request.headers))

    if response.status_code == 200:
        # Return the content - string txt instead of bytes
        return response.content.decode('utf-8')


def upload_to_s3(path, content):
    bucket_name = 'hackathon-team-5-pl'
    REGION = 'pl-waw'

    s3_client = boto3.client(
        's3',
        region_name=REGION,
        endpoint_url=f'https://{bucket_name}.s3.{REGION}.scw.cloud',
        aws_access_key_id=os.getenv('AWS_ACCESS_KEY_ID'),
        aws_secret_access_key=os.getenv('AWS_SECRET_ACCESS_KEY'),
    )

    fields = {
        "acl": "private",
        "Cache-Control": "nocache",
        "Content-Type": "application/json"
    }

    try:
        presigned = s3_client.generate_presigned_post(
            Bucket=bucket_name,
            Key=path,
            Fields=fields,
            Conditions=[
                {"acl": "private"},
                {"Cache-Control": "nocache"},
                {"Content-Type": "application/json"},
                {"key": path}
            ],
            ExpiresIn=120
        )
    except ClientError as e:
        print(f"[ERROR] Failed to generate presigned URL: {e}")
        return False

    # upload JSON as string
    files = {'file': ('file', content, 'application/json')}
    response = requests.post(presigned['url'], data=presigned['fields'], files=files)

    if response.status_code == 204:
        print("[INFO] Upload successful")
        return True
    else:
        print("[ERROR] Upload failed:", response.status_code, response.content)
        return False




def handle_message(body, attributes):
    message = json.loads(json.loads(body).get("Message", {}))
    print(f"[AGENTS] Received message: {message}")
    if 'articleId' not in message or 'articlePath' not in message or 'category' not in message:
        print("[ERROR] Invalid message format.")
        return

    # Steps:
    # 1. Read article from S3 
    # 2. Check whether or not the article is relevant to the category
    # 3. If relevant, generate summary, tags, importance score, sentiment score, source reliability score
    # 4. Store summary and text content to S3
    # 5. Send message to another SQS queue with summary_path, tags, category, importance_score, sentiment_score, source_reliability_score

    article_content = download_from_s3(message["articlePath"])
    if not article_content:
        print("[ERROR] Failed to download article from S3.")
        return
    
    state = {
        "article_text": article_content,
        "category": message["category"]
    }

    result = runnable.invoke(state)

    if not result.get("relevance", False):
        print("[INFO] Article not relevant, skipping.")
        return

    summary_path = f'summaries/article_{message["articleId"]}_summary.json'
    summary_content = json.dumps({
        "summary": result.get("summary", "")
    })
    print("[INFO] Uploading summary to S3 at", summary_path)
    upload_to_s3(summary_path, summary_content)

    payload = {
        "articleId": message["articleId"],
        "articleSummaryPath": summary_path,
        "oneLineSummary": result.get("one_liner", ""),
        "keywords": result.get("keywords", []),
        "importanceScore": result.get("importance_score", 0),
        "sentimentScore": result.get("sentiment_score", 0),
        "sourceReliabilityScore": result.get("source_reliability_score", 0),
        "summary": result.get("summary", "")
    }
    queue_out.send_message(MessageBody=json.dumps(payload))


def main():
    print("[AGENTS] Module started. Listening for messages...")

    while True:
        messages = queue_in.receive_messages(
            MessageAttributeNames=["All"],
            MaxNumberOfMessages=10,
            WaitTimeSeconds=5
        )

        if not messages:
            continue

        for msg in messages:
            try:
                handle_message(msg.body, msg.message_attributes)
                msg.delete()
                print(f"[AGENTS] Message {msg.message_id} deleted.")
            except Exception as e:
                print(f"[ERROR] Failed to process {msg.message_id}: {e}")

        time.sleep(1)

if __name__ == "__main__":
    main()
