import boto3
import os
import time
import json
from dotenv import load_dotenv

load_dotenv()

sqs = boto3.resource('sqs',
    endpoint_url=os.environ.get('SQS_URL_RAW'),
    aws_access_key_id=os.environ.get('SQS_ADMIN_ACCESS_KEY_ID'),
    aws_secret_access_key=os.environ.get('SQS_ADMIN_SECRET_KEY'),
    region_name='fr-par'
)

queue = sqs.get_queue_by_name(QueueName='queue-raw-articles')

def handle_message(body, attributes):
    message = json.loads(body)
    if 'article_path' not in message or 'category' not in message:
        print("[ERROR] Invalid message format.")
        return
    article_path = message['article_path']
    category = message['category']
    # TODO: agent logic, calls to LLMs, etc.
    print(f"[AGENTS] [MOCK] Article Path: {article_path}, Category: {category}")



def main():
    print("[AGENTS] Module started. Listening for messages...")

    while True:
        messages = queue.receive_messages(
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
