import os
import logging
import boto3
import uuid
import requests
from botocore.exceptions import ClientError
from dotenv import load_dotenv

load_dotenv()

AWS_ACCESS_KEY_ID = os.getenv('AWS_ACCESS_KEY_ID') or ''
AWS_SECRET_ACCESS_KEY = os.getenv('AWS_SECRET_ACCESS_KEY') or ''

bucket_name = 'hackathon-team-5'
object_name = 'test.txt'
# Generate a presigned URL for the object
session = boto3.session.Session()

s3_client = session.client(
    service_name='s3',
    region_name='fr-par',
    use_ssl=True,
    endpoint_url='https://hackathon-team-5.s3.fr-par.scw.cloud',
    aws_access_key_id=AWS_ACCESS_KEY_ID,
    aws_secret_access_key=AWS_SECRET_ACCESS_KEY,
)


print("Downloading file using presigned URL...")
with open('downloaded_test_file.txt', 'wb') as f:
    s3_client.download_fileobj(bucket_name, object_name, f)

print("File downloaded as 'downloaded_test_file.txt'")
