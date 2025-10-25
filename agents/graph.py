import json
import os
from typing import TypedDict
from langgraph.graph import StateGraph, END

from dotenv import load_dotenv
load_dotenv()


from openai import OpenAI
from prompts import SYSTEM_PROMPT, RELEVANCE_PROMPT, SUMMARIZER_PROMPT, SCORING_PROMPT


client = OpenAI(
    base_url = "https://api.scaleway.ai/d6f62684-5d48-4151-97d7-3364fa5ac43a/v1",
    api_key = os.getenv("SCW_SECRET_KEY")
)


class State(TypedDict, total=False):
    article_text: str
    category: str
    keywords: list[str]
    relevance: bool
    importance_score: int
    sentiment_score: int
    source_reliability_score: int
    one_liner: str
    summary: str


def relevance_node(state: State):
    print("[INFO] Relevance node started...")
    response = client.chat.completions.create(
        model="gpt-oss-120b",
        messages=[
            { "role": "system", "content": SYSTEM_PROMPT },
            { "role": "user", "content": RELEVANCE_PROMPT.replace("{{article_text}}", state["article_text"]).replace("{{category}}", state["category"]) },
        ],
        temperature=1,
        top_p=1,
        presence_penalty=0
    )

    try:
        content = response.choices[0].message.content
        data = json.loads(content)
    except Exception as e:
        print("[ERROR] Invalid JSON:", e)
        return {"relevance": False}

    if "relevance" not in data:
        print("[ERROR] Key 'relevance' missing in JSON.")
        return {"relevance": False}

    return {"relevance": bool(data["relevance"])}


def summarizer_node(state: State):
    print("[INFO] Summarizer node started...")
    prompt = SUMMARIZER_PROMPT.replace("{{article_text}}", state["article_text"]).replace("{{category}}", state["category"])

    response = client.chat.completions.create(
        model="gpt-oss-120b",
        messages=[
            {"role": "system", "content": SYSTEM_PROMPT},
            {"role": "user", "content": prompt},
        ],
        temperature=0.7,
    )

    data = json.loads(response.choices[0].message.content)
    return {
        "summary": data["summary"],
        "one_liner": data["one_liner"],
        "keywords": data["keywords"]
    }


def scoring_node(state: State):
    print("[INFO] Scoring node started...")
    prompt = SCORING_PROMPT.replace("{{article_text}}", state["article_text"]).replace("{{category}}", state["category"])

    response = client.chat.completions.create(
        model="gpt-oss-120b",
        messages=[
            {"role": "system", "content": SYSTEM_PROMPT},
            {"role": "user", "content": prompt},
        ],
        temperature=0.2,
    )

    data = json.loads(response.choices[0].message.content)
    return {
        "importance_score": int(data["importance_score"]),
        "sentiment_score": int(data["sentiment_score"]),
        "source_reliability_score": int(data["source_reliability_score"])
    }


def empty_result_node(state: State):
    print("[INFO] Empty result node started...")
    return {
        "summary": "",
        "one_liner": "",
        "keywords": [],
        "importance_score": 0,
        "sentiment_score": 0,
        "source_reliability_score": 0
    }


graph = StateGraph(State)

# nodes
graph.add_node("relevance", relevance_node)
graph.add_node("summarizer", summarizer_node)
graph.add_node("scoring", scoring_node)
graph.add_node("empty_result", empty_result_node)


graph.set_entry_point("relevance")

graph.add_conditional_edges(
    "relevance",
    lambda state: "continue" if state.get("relevance") else "empty",
    {
        "continue": "summarizer",
        "empty": "empty_result"
    }
)

graph.add_edge("summarizer", "scoring")
graph.add_edge("scoring", END)
graph.add_edge("empty_result", END)

runnable = graph.compile()
