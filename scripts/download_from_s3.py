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

bucket_name = 'hackathon-team-5'
object_name = 'test.txt'
REGION = 'fr-par'

s3_client = session.client(
    service_name='s3',
    region_name=REGION,
    use_ssl=True,
    endpoint_url='https://hackathon-team-5.s3.fr-par.scw.cloud',
    aws_access_key_id=AWS_ACCESS_KEY_ID,
    aws_secret_access_key=AWS_SECRET_ACCESS_KEY,
)

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

# Download the file using requests with AWS Signature Version 4

# Prepare the request
request = AWSRequest(method='GET', url=response['url'] + f"/{object_name}")

# Sign the request
credentials = boto3.Session(
    aws_access_key_id=AWS_ACCESS_KEY_ID,
    aws_secret_access_key=AWS_SECRET_ACCESS_KEY
).get_credentials()

SigV4Auth(credentials, 's3', REGION).add_auth(request)

request.headers['X-Amz-Content-Sha256'] = 'e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855'  # SHA256 hash of an empty string

print(request.headers)

# Make the request
response = requests.get(response['url'] + f"/{object_name}", headers=dict(request.headers))

if response.status_code == 200:
    # Save the file
    output_filename = f'downloaded_{object_name}'
    with open(output_filename, 'wb') as f:
        f.write(response.content)
    print(f'File downloaded successfully: {output_filename}')
else:
    print(f'Error downloading file: {response.status_code} - {response.text}')
