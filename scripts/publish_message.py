import boto3
import os
from dotenv import load_dotenv

load_dotenv()


# Get the service resource
sqs = boto3.resource('sqs',
  endpoint_url=os.environ.get('SQS_ENDPOINT_URL'),
  aws_access_key_id=os.environ.get('SQS_ADMIN_ACCESS_KEY_ID'),
  aws_secret_access_key=os.environ.get('SQS_ADMIN_SECRET_KEY'),
  region_name='fr-par')


# Create the queue. This returns an SQS.Queue instance
queue = sqs.get_queue_by_name(QueueName='queue-test')

# You can now access identifiers and attributes
print(queue.url)
print(queue.attributes)

for i in range (0,10):
  queue.send_message(MessageBody="Hello World: "+str(i))
