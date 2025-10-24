import feedparser
import logging

def parse_feed(url):
    logging.info(f"Feed parsing requested for URL: {url}")
    feed = feedparser.parse(url)
    entries = []
    for entry in feed.entries:
        entries.append({
            'title': entry.title,
            'link': entry.link,
            'summary': entry.summary
        })
    return entries