import os
import logging
import boto3
import uuid
import requests
from botocore.exceptions import ClientError
from dotenv import load_dotenv
from botocore.auth import SigV4Auth
from botocore.awsrequest import AWSRequest

load_dotenv()

AWS_ACCESS_KEY_ID = os.getenv('AWS_ACCESS_KEY_ID') or ''
AWS_SECRET_ACCESS_KEY = os.getenv('AWS_SECRET_ACCESS_KEY') or ''

# Generate a presigned URL for the object
session = boto3.session.Session()

bucket_name = 'hackathon-team-5-pl'
object_name = 'test.txt'
REGION = 'pl-waw'

url = f'https://hackathon-team-5-pl.s3.pl-waw.scw.cloud/{object_name}'

# Prepare the request
request = AWSRequest(method='GET', url=url, data=b'')

# Sign the request
credentials = boto3.Session(
    aws_access_key_id=AWS_ACCESS_KEY_ID,
    aws_secret_access_key=AWS_SECRET_ACCESS_KEY
).get_credentials()

SigV4Auth(credentials, 's3', REGION).add_auth(request)

request.headers['X-Amz-Content-Sha256'] = 'e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855'  # SHA256 hash of an empty string

# Make the request
response = requests.get(url, headers=dict(request.headers))

if response.status_code == 200:
    # Save the file
    output_filename = f'downloaded_{object_name}'
    with open(output_filename, 'wb') as f:
        f.write(response.content)
    print(f'File downloaded successfully: {output_filename}')
else:
    print(f'Error downloading file: {response.status_code} - {response.text}')
