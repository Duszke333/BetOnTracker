package org.betonskm.orchestrator.adapter.event.listener.news;

import io.awspring.cloud.sqs.annotation.SqsListener;
import io.awspring.cloud.sqs.annotation.SqsListenerAcknowledgementMode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.betonskm.orchestrator.adapter.event.listener.news.model.NewsArticleEvent;
import org.betonskm.orchestrator.configuration.transactionoutbox.TransactionInbox;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class NewsArticlesListener {

  private final TransactionInbox transactionInbox;

  @Transactional
  @SqsListener(value = "${aws.sqs.news-articles-queue-url}", acknowledgementMode = SqsListenerAcknowledgementMode.ON_SUCCESS)
  public void onNewsArticlesEvent(NewsArticleEvent event) {
    log.info("[NEWS LISTENER] Received news articles event: {}", event);
    transactionInbox.schedule(this.getClass()).process(event);
  }

  public void process(NewsArticleEvent event) {
    log.info("[NEWS LISTENER] Processing");
  }
}
