import feedparser

def parse_feed(url):
    feed = feedparser.parse(url)
    entries = []
    for entry in feed.entries:
        entries.append({
            'title': entry.title,
            'link': entry.link,
            'summary': entry.summary
        })
    return entries