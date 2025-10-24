import os
from dotenv import load_dotenv
load_dotenv()

from openai import OpenAI

client = OpenAI(
    base_url = "https://api.scaleway.ai/d6f62684-5d48-4151-97d7-3364fa5ac43a/v1",
    api_key = os.getenv("SCW_SECRET_KEY")
)


MSG = """Analyze the following news article and provide a brief summary along with the main points discussed.

Article: 
<item>
<title>Punkt informacyjny dla mieszkańców Zaspy</title>
<link>
https://ztm.gda.pl/wiadomosci/punkt-informacyjny-dla-mieszkancow-zaspy,a,11109
</link>
<guid>
https://ztm.gda.pl/wiadomosci/punkt-informacyjny-dla-mieszkancow-zaspy,a,11109
</guid>
<description>
W związku z planowaną rozbiórką kładki nad al. Rzeczypospolitej na gdańskiej Zaspie, uruchomione zostaną specjalne punkty informacyjne dla mieszkańców.
</description>
<content:encoded>
W związku z planowaną rozbiórką kładki nad al. Rzeczypospolitej na gdańskiej Zaspie, uruchomione zostaną specjalne punkty informacyjne dla mieszkańców.
</content:encoded>
<pubDate>Tue, 21 Oct 2025 09:44:49 +0200</pubDate>
</item>
"""

response = client.chat.completions.create(
    model="gpt-oss-120b",
    messages=[
        { "role": "system", "content": "You are an assistant analyzing a news article from a feed." },
        { "role": "user", "content": MSG },
    ],
    max_tokens=512,
    temperature=1,
    top_p=1,
    presence_penalty=0,
    stream=True
)


for chunk in response:
    if chunk.choices and chunk.choices[0].delta.content:
        print(chunk.choices[0].delta.content, end="", flush=True)
        with open("output.log", "a", encoding="utf-8") as f:
            f.write(chunk.choices[0].delta.content)