from . import feeds

def main() -> None:
    print("Hello from rss!")
    print(feeds.parse_feed("resources/test_rss.xml"))
