# Queues specification

## `queue-raw-articles`

Body structure:
```json
{
    "article_path": "path/to/raw/article",
    "category": "one_of_categories"
}
```

## `queue-summaries`

Body structure:
```json
{
    "article_path": "/path/to/summary.txt",
    "category": "one_of_categories",
    "tags": ["tag1", "tag2"],
    "importance_score": "1-to-5",
    "sentiment_score": "1-to-5",
    "source_reliability_score": "1-to-5"
}
```