from . import feeds
import logging

def main() -> None:
    logging.basicConfig(level=logging.INFO)
    logging.info("News module initialized.")
    feeds.parse_feed('https://feedparser.readthedocs.io/en/latest/examples/rss20.xml')