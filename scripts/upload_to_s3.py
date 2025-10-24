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


# Generate a presigned URL for the object
session = boto3.session.Session()

s3_client = session.client(
    service_name='s3',
    region_name='pl-waw',
    use_ssl=True,
    endpoint_url='https://hackathon-team-5-pl.s3.pl-waw.scw.cloud',
    aws_access_key_id=AWS_ACCESS_KEY_ID,
    aws_secret_access_key=AWS_SECRET_ACCESS_KEY,
)

bucket_name = 'hackathon-team-5-pl'
object_name = f'test.txt'
fields = {
        "acl": "private",
        "Cache-Control": "nocache",
        "Content-Type": "text/plain"
    }
conditions = [
    {"key": object_name},
    {"acl": "private"},
    {"Cache-Control": "nocache"},
    {"Content-Type": "text/plain"}
]
expiration = 120 # Max two minutes to start upload

try:
    response = s3_client.generate_presigned_post(Bucket=bucket_name,
                                                    Key=object_name,
                                                    Fields=fields,
                                                    Conditions=conditions,
                                                    ExpiresIn=expiration)
except ClientError as e:
    logging.error(e)
    exit()

# The response contains the presigned URL and required fields
print(response)


with open('test_file.txt', 'rb') as f:
    files = {'file': (object_name, f)}
    http_response = requests.post(response['url'], data=response['fields'], files=files)
    print(http_response.content)
