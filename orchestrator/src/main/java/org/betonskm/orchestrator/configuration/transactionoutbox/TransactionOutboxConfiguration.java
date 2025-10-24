package org.betonskm.orchestrator.configuration.transactionoutbox;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gruelbox.transactionoutbox.DefaultPersistor;
import com.gruelbox.transactionoutbox.Dialect;
import com.gruelbox.transactionoutbox.Submitter;
import com.gruelbox.transactionoutbox.TransactionOutbox;
import com.gruelbox.transactionoutbox.jackson.JacksonInvocationSerializer;
import com.gruelbox.transactionoutbox.spring.SpringInstantiator;
import com.gruelbox.transactionoutbox.spring.SpringTransactionManager;
import java.time.Clock;
import java.util.Optional;
import java.util.concurrent.Executors;
import lombok.RequiredArgsConstructor;
import org.betonskm.orchestrator.configuration.transactionoutbox.TransactionOutboxProperties.SchedulingProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@Import({SpringInstantiator.class, SpringTransactionManager.class})
@RequiredArgsConstructor
public class TransactionOutboxConfiguration {

  private final SpringTransactionManager transactionManager;
  private final SpringInstantiator springInstantiator;
  private final ObjectMapper objectMapper;
  private final Clock clock;
  private final TransactionOutboxProperties properties;

  @Bean
  public TransactionInbox transactionInbox(Optional<Submitter> submitter) {
    return new TransactionInbox(
        transactionOutbox(properties.getInboxEvent(), submitter.orElseGet(this::submitter))
    );
  }

  @Bean
  public TransactionOutbox transactionOutbox(Optional<Submitter> submitter) {
    return transactionOutbox(properties.getPublishEvent(), submitter.orElseGet(this::submitter));
  }

  private Submitter submitter() {
    SemaphoreBoundedExecutor executor = new SemaphoreBoundedExecutor(
        Executors.newVirtualThreadPerTaskExecutor(),
        properties.getMaxConcurrentTasks()
    );
    return Submitter.withExecutor(executor);
  }

  private TransactionOutbox transactionOutbox(SchedulingProperties properties, Submitter submitter) {
    DefaultPersistor persistor = DefaultPersistor.builder()
        .dialect(Dialect.POSTGRESQL_9)
        .tableName(properties.getTableName())
        .migrate(false)
        .serializer(JacksonInvocationSerializer.builder().mapper(objectMapper).build())
        .build();

    return TransactionOutbox.builder()
        .instantiator(springInstantiator)
        .transactionManager(transactionManager)
        .attemptFrequency(properties.getAttemptFrequency())
        .blockAfterAttempts(properties.getBlockAfterAttempts())
        .flushBatchSize(properties.getFlushBatchSize())
        .persistor(persistor)
        .submitter(submitter)
        .serializeMdc(true)
        .clockProvider(() -> clock)
        .build();
  }
}
