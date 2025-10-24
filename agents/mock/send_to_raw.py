import boto3
import os
from dotenv import load_dotenv
import json

load_dotenv()


# Get the service resource
sqs = boto3.resource('sqs',
    endpoint_url=os.environ.get('SQS_URL_RAW'),
    aws_access_key_id=os.environ.get('SQS_ADMIN_ACCESS_KEY_ID'),
    aws_secret_access_key=os.environ.get('SQS_ADMIN_SECRET_KEY'),
    region_name='fr-par')


# Create the queue. This returns an SQS.Queue instance
queue = sqs.get_queue_by_name(QueueName='queue-raw-articles')

# You can now access identifiers and attributes
print(queue.url)
print(queue.attributes)

# for i in range (0,10):
#     payload = {
#         "article_path": "/feeds/raw/telecom/article_"+str(i)+".txt",
#         "category": "telecom",
#     }
#     queue.send_message(MessageBody=json.dumps(payload))

payload = {
    "article_path": "/feeds/raw/telecom/article_1.txt",
    "category": "telecom",
}
queue.send_message(MessageBody=json.dumps(payload))

payload = {
    "article_path": "/feeds/raw/phones/article_1.txt",
    "category": "apple",
}
queue.send_message(MessageBody=json.dumps(payload))

payload = {
    "article_path": "/feeds/raw/phones/article_1.txt",
    "category": "computers",
}
queue.send_message(MessageBody=json.dumps(payload))

payload = {
    "article_path": "/feeds/raw/phones/article_1.txt",
    "category": "fruits",
}
queue.send_message(MessageBody=json.dumps(payload))