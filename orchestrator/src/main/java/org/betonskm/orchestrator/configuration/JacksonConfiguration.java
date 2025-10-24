package org.betonskm.orchestrator.configuration;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import org.betonskm.orchestrator.configuration.serialization.OffsetDateTimeSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfiguration {

  private static final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";

  @Bean
  public JavaTimeModule javaTimeModule() {
    JavaTimeModule javaTimeModule = new JavaTimeModule();
    javaTimeModule.addSerializer(OffsetDateTime.class, new OffsetDateTimeSerializer(
        DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)));
    return javaTimeModule;
  }
}
