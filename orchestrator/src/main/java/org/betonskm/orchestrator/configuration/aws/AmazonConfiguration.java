package org.betonskm.orchestrator.configuration.aws;

import static org.apache.commons.lang3.ObjectUtils.allNotNull;

import java.net.URI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.awscore.client.builder.AwsClientBuilder;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(AmazonProperties.class)
public class AmazonConfiguration {

  private final AmazonProperties amazonProperties;

  @Bean
  public SqsAsyncClient sqsAsyncClient() {
    return apply(amazonProperties.getSqs().getEndpoint(), SqsAsyncClient.builder())
        .region(Region.of(amazonProperties.getSqs().getRegion()))
        .credentialsProvider(buildSqsCredentialsProvider())
        .build();
  }

  private AwsCredentialsProvider buildSqsCredentialsProvider() {
    if (allNotNull(amazonProperties.getSqs().getAccessKeyId(), amazonProperties.getSqs().getSecretKey())) {
      return () -> AwsBasicCredentials.create(
          amazonProperties.getSqs().getAccessKeyId(),
          amazonProperties.getSqs().getSecretKey()
      );
    } else {
      return DefaultCredentialsProvider.create();
    }
  }

  private <T extends AwsClientBuilder<T, C>, C> AwsClientBuilder<T, C> apply(String endpoint, AwsClientBuilder<T, C> builder) {
    if (StringUtils.isNotEmpty(endpoint)) {
      builder.endpointOverride(URI.create(endpoint));
    }
    return builder;
  }
}
