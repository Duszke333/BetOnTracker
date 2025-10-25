import os
import json
from dotenv import load_dotenv
from prompts import SYSTEM_PROMPT, RELEVANCE_PROMPT
load_dotenv()

from openai import APIError, BadRequestError, OpenAI

client = OpenAI(
    base_url = "https://api.scaleway.ai/d6f62684-5d48-4151-97d7-3364fa5ac43a/v1",
    api_key = os.getenv("SCW_SECRET_KEY")
)


def evaluate_relevance(article_text: str, category: str):
    response = client.chat.completions.create(
        model="gpt-oss-120b",
        messages=[
            { "role": "system", "content": SYSTEM_PROMPT },
            { "role": "user", "content": RELEVANCE_PROMPT.replace("{{article_text}}", article_text).replace("{{category}}", category) },
        ],
        # response_format={
        #     "type": "json_schema",
        #     "schema": {
        #         "type": "object",
        #         "properties": {
        #             "relevant": { "type": "boolean" }
        #         },
        #         "required": ["relevant"]
        #     }
        # },
        max_tokens=50,
        temperature=1,
        top_p=1,
        presence_penalty=0
    )

    try:
        content = response.choices[0].message.content
        data = json.loads(content)
    except Exception as e:
        print("[ERROR] Invalid JSON:", e)
        return False

    if "relevant" not in data:
        print("[ERROR] Key 'relevant' missing in JSON.")
        return False

    return bool(data["relevant"])
