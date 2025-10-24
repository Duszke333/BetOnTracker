from . import feeds, upload_to_s3
import logging

def main() -> None:
    logging.basicConfig(level=logging.INFO)
    logging.info("News module initialized.")
    entries, _, _ = feeds.parse_feed('https://feedparser.readthedocs.io/en/latest/examples/rss20.xml')
    for entry in entries:
        content = f"Title: {entry['title']}\nSummary: {entry['summary'].value}\n"
        upload_to_s3.upload_to_s3(content)
    logging.info("News module processing completed.")