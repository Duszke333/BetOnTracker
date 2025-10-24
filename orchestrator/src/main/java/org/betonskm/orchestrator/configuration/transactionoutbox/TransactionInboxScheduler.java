package org.betonskm.orchestrator.configuration.transactionoutbox;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@ConditionalOnProperty(name = "app.scheduling.inbox-event.scheduler-enabled", havingValue = "true")
@RequiredArgsConstructor
public class TransactionInboxScheduler {

  private final TransactionInbox transactionInbox;

  @Scheduled(fixedDelayString = "${app.scheduling.publish-event.fixed-delay}", initialDelayString = "10s")
  public void transactionInboxScheduledTask() {
    log.info("[TransactionInboxScheduler] - Initiating flush of message batch.");
    if (!Thread.interrupted()) {
      try {
        while (transactionInbox.flush()) {
          log.debug("[TransactionInboxScheduler] - Flushing the message.");
        }
      } catch (Exception e) {
        log.error("[TransactionInboxScheduler] - Error during flushing messages from Transaction Inbox.", e);
      }
    }
  }
}
