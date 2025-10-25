package org.betonskm.orchestrator.configuration.aws;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@AllArgsConstructor
@ConfigurationProperties(prefix = "aws")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AmazonProperties {

  private AmazonSqsProperties sqs;
  private AmazonSnsProperties sns;

  @Getter
  @Setter
  @AllArgsConstructor
  public static class AmazonSqsProperties {

    private String accessKeyId;
    private String secretKey;
    private String endpoint;
    private String region;
    private String newsArticlesQueueUrl;
    private String articleSummaryQueueUrl;
  }

  @Getter
  @Setter
  @AllArgsConstructor
  public static class AmazonSnsProperties {

    private String accessKeyId;
    private String secretKey;
    private String endpoint;
    private String region;
    private String summarizeArticlesTopicArn;
  }
}
