package org.betonskm.orchestrator.configuration.aws;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "aws")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AmazonProperties {

  private final String accessKeyId;
  private final String secretKey;
  private AmazonSqsProperties sqs;

  @Getter
  @Setter
  @RequiredArgsConstructor
  public static class AmazonSqsProperties {

    private String endpoint;
    private String region;
    private String newsArticlesQueueUrl;
  }
}
