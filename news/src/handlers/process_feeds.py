import logging
from concurrent.futures import ThreadPoolExecutor
import json
import feedparser
import os
import boto3
import uuid
import requests
from botocore.exceptions import ClientError
from dotenv import load_dotenv
from io import BytesIO
from datetime import datetime
from playwright.sync_api import sync_playwright

def get_page_content(url):
    with sync_playwright() as p:
        browser = p.chromium.launch()
        page = browser.new_page()
        page.goto(url)
        content = page.evaluate('''() => {
            // Try multiple strategies to find the article content
            let container = null;
            
            // Strategy 1: Look for <article> tag
            container = document.querySelector('article');
            
            // Strategy 2: Look for common article-related classes/ids
            if (!container) {
                const selectors = [
                    '[class*="article"]',
                    '[id*="article"]',
                    '[class*="content"]',
                    '[id*="content"]',
                    '[class*="post"]',
                    '[id*="post"]',
                    'main',
                    '[role="main"]'
                ];
                
                for (const selector of selectors) {
                    container = document.querySelector(selector);
                    if (container) break;
                }
            }
            
            // Strategy 3: Fallback to body if nothing found
            if (!container) {
                container = document.body;
            }
            
            const elements = container.querySelectorAll('h1, h2, h3, h4, h5, h6, p');
            return Array.from(elements)
                .filter(el => {
                    const style = window.getComputedStyle(el);
                    const isVisible = style.display !== 'none' && 
                                     style.visibility !== 'hidden' &&
                                     style.opacity !== '0';
                    return isVisible;
                })
                .map(el => el.innerText.trim())
                .filter(text => text.length > 0)
                .join('\\n\\n');
        }''')
        
        browser.close()
        return content

def parse_feed(url, etag=None, modified=None):
    logging.info(f"Feed parsing requested for URL: {url}")
    if not etag and not modified:
        logging.warning('No etag or modified date provided; performing full fetch.')
    feed = feedparser.parse(url, etag=etag, modified=modified)
    if feed.entries:
        parsed_entries = []
        for entry in feed.entries:
            content = get_page_content(entry.link)
            logging.info(f"Fetched content for entry: {entry.link}")
            parsed_entries.append({
                'title': entry.title,
                'link': entry.link,
                'description': entry.description,
                'date': entry.published_parsed,
                'content': content
            })
        logging.info(f"Parsed {len(parsed_entries)} entries from the feed.")
        return parsed_entries, feed.get('etag'), feed.get('modified_parsed')
    else:
        logging.info("No new entries found in the feed.")
        return [], feed.get('etag'), feed.get('modified_parsed')

def upload_to_s3(content, object_name):
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
    fields = {
            "acl": "private",
            "Cache-Control": "nocache",
            "Content-Type": "application/json"
        }
    conditions = [
        {"key": object_name},
        {"acl": "private"},
        {"Cache-Control": "nocache"},
        {"Content-Type": "application/json"}
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

    stream = BytesIO(content.encode('utf-8'))
    files = {'file': (object_name, stream)}
    http_response = requests.post(response['url'], data=response['fields'], files=files)
    print(http_response.content)

def send_queue_message(message_body):
    load_dotenv()

    # Get the service resource
    sqs = boto3.resource('sqs',
      endpoint_url=os.environ.get('SQS_ENDPOINT_URL'),
      aws_access_key_id=os.environ.get('SQS_ADMIN_ACCESS_KEY_ID'),
      aws_secret_access_key=os.environ.get('SQS_ADMIN_SECRET_KEY'),
      region_name='fr-par')


    # Create the queue. This returns an SQS.Queue instance
    queue = sqs.get_queue_by_name(QueueName='queue-new-articles')

    # You can now access identifiers and attributes
    print(queue.url)
    print(queue.attributes)

    queue.send_message(MessageBody=message_body)

def main():
    logging.basicConfig(level=logging.INFO)
    logging.info("News module initialized.")

    urls = [
        'https://www.telepolis.pl/rss'
    ]

    with ThreadPoolExecutor(max_workers=5) as executor:
        futures = {executor.submit(parse_feed, url): url for url in urls}
        for future in futures:
            url = futures[future]
            try:
                entries, etag, modified = future.result()
                logging.info(f"Fetched {len(entries)} entries from {url}")
                for entry in entries:
                    obj_name = f'articles/{str(uuid.uuid4())}.json'
                    upload_to_s3(json.dumps(entry), obj_name)
                    
                    # Convert modified tuple to ISO format string with timezone
                    fetched_at = None
                    if modified:
                        # modified is a time.struct_time, use first 6 elements for datetime
                        dt = datetime(*modified[:6])
                        # Format: yyyy-MM-dd'T'HH:mm:ss.SSSXXX
                        # Add milliseconds (000) and timezone offset (+00:00 for UTC)
                        fetched_at = dt.strftime('%Y-%m-%dT%H:%M:%S.000+00:00')
                    
                    message_data = {
                        'feedUrl': url,
                        'articleUrl': entry['link'],
                        'articleTitle': entry['title'],
                        's3path': obj_name,
                        'etag': etag,
                        'fetchedAt': fetched_at
                    }
                    send_queue_message(json.dumps(message_data))
                    logging.info(f"Sent message for article: {entry['title']}")
            except Exception as e:
                logging.error(f"Error fetching feed from {url}: {e}")

    logging.info("News module processing completed.")

if __name__ == "__main__":
    main()