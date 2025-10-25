package org.betonskm.orchestrator.configuration.transactionoutbox;

import java.time.Duration;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "app.scheduling")
public class TransactionOutboxProperties {

  private Integer maxConcurrentTasks;
  private SchedulingProperties publishEvent;
  private SchedulingProperties inboxEvent;

  @Data
  public static class SchedulingProperties {
    private Boolean schedulerEnabled;
    private String tableName;
    private String fixedDelay;
    private Duration attemptFrequency;
    private Integer blockAfterAttempts;
    private Integer flushBatchSize;
  }
}
