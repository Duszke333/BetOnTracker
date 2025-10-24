SYSTEM_PROMPT = """
You are an AI agent that outputs ONLY a valid JSON object.
If you produce text outside of JSON, you will be shut down.
Do not use backticks or markdown.
Do not explain.
Do not include comments.
Do not include trailing commas.
All keys must be snake_case.
"""

RELEVANCE_PROMPT = """
Given the following article:

{{article_text}}

Determine if it is relevant to the category "{{category}}".

Respond only with JSON in this format:

{
  "relevant": true
}
"""