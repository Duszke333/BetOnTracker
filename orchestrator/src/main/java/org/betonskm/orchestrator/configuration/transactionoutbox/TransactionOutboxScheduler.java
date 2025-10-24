package org.betonskm.orchestrator.configuration.transactionoutbox;

import com.gruelbox.transactionoutbox.TransactionOutbox;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@ConditionalOnProperty(name = "app.scheduling.publish-event.scheduler-enabled", havingValue = "true")
@RequiredArgsConstructor
public class TransactionOutboxScheduler {

  private final TransactionOutbox transactionOutbox;

  @Scheduled(fixedDelayString = "${app.scheduling.publish-event.fixed-delay}", initialDelayString = "10s")
  public void transactionOutboxScheduledTask() {
    log.info("[TransactionOutboxScheduler] - Initiating flush of message batch.");
    if (!Thread.interrupted()) {
      try {
        while (transactionOutbox.flush()) {
          log.debug("[TransactionOutboxScheduler] - Flushing the message.");
        }
      } catch (Exception e) {
        log.error("[TransactionOutboxScheduler] - Error during flushing messages from Transaction Outbox.", e);
      }
    }
  }
}
