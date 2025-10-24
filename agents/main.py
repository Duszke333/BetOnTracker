import boto3
import os
import time
import json
from dotenv import load_dotenv

load_dotenv()

sqs = boto3.resource('sqs',
    endpoint_url=os.environ.get('SQS_URL_RAW'),
    aws_access_key_id=os.environ.get('SQS_ADMIN_ACCESS_KEY_ID'),
    aws_secret_access_key=os.environ.get('SQS_ADMIN_SECRET_KEY'),
    region_name='fr-par'
)

queue = sqs.get_queue_by_name(QueueName='queue-raw-articles')

# Needed outputs (if relevant):
# To S3 - Summary, text content
# To SQS - summary_path, tags, category, importance_score, sentiment_score, source_reliability_score

S3_MOCK_CONTENT = """
<title>
Apple dało ciała. Nowy MacBook Pro z M5 dobija do 102℃
</title>

<description>
<p>Niestety, fizyki nie da się oszukać - smukła obudowa i mały radiator oznaczają wysokie temperatury pod obciążeniem. Jest lepiej niż było, ale nadal źle.</p><p>Podobnie jak poprzednik, nowy <strong><a href="https://www.telepolis.pl/tech/sprzet/apple-m5-specyfikacja-wydajnosc">Apple M5</a></strong> oferuje swoim użytkownikom <strong>tylko 10 rdzeni</strong> w konfiguracji 4 duże, wysokowydajne i 6 małych, energooszczędnych. <strong>Amerykanie nie zdecydowali się więc na zmianę układu chłodzenia</strong> - to nadal jeden ciepłowód, jeden wentylator i malutki radiator. <strong>I kolejny raz są problemy z wysokimi temperaturami.</strong></p><p><h3><strong>Dopiero MacBooki z M5 Pro i M5 Max dostaną dwa wentylatory</strong></h3></p><p>Według pomiarów przeprowadzonych przez kanał <a href="https://www.youtube.com/watch?v=Clwet4ckP2A">"Max Tech"</a> na YouTube, gdzie porównywany jest procesor Apple M4 i M5 w laptopach MacBook Pro, <strong>nowy chip dobija do 102℃</strong>. Dochodzi więc do zjawiska <strong>thermal throttlingu</strong>, czyli tzw. dławienia termicznego. <strong>Uruchamia się wtedy zabezpieczenie przed przegrzaniem </strong>i sztucznie ograniczana jest wydajność.</p><p><img src="https://pliki.telepolis.pl/file/237992/original.jpg" alt="Apple dało ciała. Nowy MacBook Pro z M5 dobija do 102℃" loading="lazy" data-image-id="237992" data-image-squared="false"></p><p>Aby <strong>obciążyć CPU w 100%</strong> użyto popularnego programu <strong>Cinebench R24</strong><strong>,</strong> a więc takich temperatur nie zobaczy się podczas surfowania po sieci czy oglądania filmów i seriali. Jednak przy bardziej wymagającej pracy już tak.</p><p><img src="https://pliki.telepolis.pl/file/237993/original.jpg" alt="Apple dało ciała. Nowy MacBook Pro z M5 dobija do 102℃" loading="lazy" data-image-id="237993" data-image-squared="false"></p><p>Warto zaznaczyć, że <strong>Apple M5 i tak wypada lepiej niż M4</strong>. Nowy procesor jest o około 14℃ chłodniejszy, gdyż stary w tych samych warunkach osiąga 116℃. Spowodowane to jest zapewne <strong>wykorzystaniem lepszej litografii (TSMC N3P vs N3E</strong>). Jest też szansa, że Gigant z Cupertino sięgnął po lepszą pastę termoprzewodząca/termopad, np. Honeywell PTM7950.</p><p>Osoby, którym zależy na niskich temperaturach i cichej pracy powinny wstrzymać się z zakupem laptopa. Według ostatnich doniesień w pierwszej połowie przyszłego roku pojawią się rozwiązania z mocniejszymi chipami M5 Pro i M5 Max, które dostają lepszy układ chłodzenia z dwoma wentylatorami.</p><p><iframe width="560" height="315" src="https://www.youtube.com/embed/Clwet4ckP2A" frameborder="0" allowfullscreen></iframe></p>
</description>
"""

def handle_message(body, attributes):
    message = json.loads(body)
    if 'article_path' not in message or 'category' not in message:
        print("[ERROR] Invalid message format.")
        return
    article_path = message['article_path']
    category = message['category']

    # TODO: agent logic, calls to LLMs, etc.
    print(f"[AGENTS] [MOCK] Article Path: {article_path}, Category: {category}")

    # Steps:
    # 1. Read article from S3 
    # 2. Check whether or not the article is relevant to the category
    # 3. If relevant, generate summary, tags, importance score, sentiment score, source reliability score
    # 4. Store summary and text content to S3
    # 5. Send message to another SQS queue with summary_path, tags, category, importance_score, sentiment_score, source_reliability_score


def main():
    print("[AGENTS] Module started. Listening for messages...")

    while True:
        messages = queue.receive_messages(
            MessageAttributeNames=["All"],
            MaxNumberOfMessages=10,
            WaitTimeSeconds=5
        )

        if not messages:
            continue

        for msg in messages:
            try:
                handle_message(msg.body, msg.message_attributes)
                msg.delete()
                print(f"[AGENTS] Message {msg.message_id} deleted.")
            except Exception as e:
                print(f"[ERROR] Failed to process {msg.message_id}: {e}")

        time.sleep(1)

if __name__ == "__main__":
    main()
