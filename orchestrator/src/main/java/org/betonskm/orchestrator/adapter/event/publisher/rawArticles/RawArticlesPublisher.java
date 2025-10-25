package org.betonskm.orchestrator.adapter.event.publisher.rawArticles;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gruelbox.transactionoutbox.TransactionOutbox;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.betonskm.orchestrator.adapter.event.publisher.rawArticles.model.RawArticleDto;
import org.betonskm.orchestrator.application.command.PublishRawArticleCommand;
import org.betonskm.orchestrator.configuration.aws.AmazonProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.amazon.awssdk.services.sns.SnsClient;

@Slf4j
@Service
@RequiredArgsConstructor
public class RawArticlesPublisher {

  private final SnsClient snsClient;
  private final AmazonProperties amazonProperties;
  private final ObjectMapper objectMapper;
  private final TransactionOutbox transactionOutbox;

  @Transactional
  public void publishEvent(PublishRawArticleCommand command) {
    log.info("[PUBLISHER] Scheduling publish of raw article event: {}", command);

    RawArticleDto dto = RawArticleDto.builder()
        .articleId(command.getArticleId())
        .articlePath(command.getArticlePath())
        .category(command.getCategory())
        .build();
    transactionOutbox.schedule(this.getClass()).doPublishEvent(dto);
  }

  public void doPublishEvent(RawArticleDto dto) {
    try {
      String message = objectMapper.writeValueAsString(dto);

      snsClient.publish(builder -> builder
          .topicArn(amazonProperties.getSns().getSummarizeArticlesTopicArn())
          .message(message)
      );

      log.info("[PUBLISHER] Published SNS event: {}", message);
    } catch (Exception e) {
      log.error("[PUBLISHER] Failed to publish SNS event", e);
    }
  }
}
