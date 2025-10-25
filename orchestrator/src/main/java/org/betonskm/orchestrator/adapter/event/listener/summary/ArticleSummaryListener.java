package org.betonskm.orchestrator.adapter.event.listener.summary;

import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.betonskm.orchestrator.adapter.event.listener.summary.model.ArticleSummaryEvent;
import org.betonskm.orchestrator.application.port.in.ArticleManagementUseCase;
import org.betonskm.orchestrator.configuration.transactionoutbox.TransactionInbox;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ArticleSummaryListener {

  private final TransactionInbox transactionInbox;
  private final ArticleManagementUseCase articleManagementUseCase;

  @SqsListener(value = "${aws.sqs.article-summary-queue-url}")
  public void onArticleSummaryEventReceived(ArticleSummaryEvent event) {
    log.info("[SUMMARY LISTENER] Received Article Summary event: {}", event);

    transactionInbox.schedule(this.getClass()).processEvent(event);
  }

  public void processEvent(ArticleSummaryEvent event) {
    log.info("[SUMMARY LISTENER] Processing Article Summary event: {}", event);
    articleManagementUseCase.updateArticle(event);
  }
}
