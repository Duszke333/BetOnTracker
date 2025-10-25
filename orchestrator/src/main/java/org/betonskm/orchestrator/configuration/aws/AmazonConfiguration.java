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
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.sns.SnsClient;
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

  @Bean
  public SnsClient snsClient() {
    return apply(amazonProperties.getSns().getEndpoint(), SnsClient.builder())
        .region(Region.of(amazonProperties.getSns().getRegion()))
        .credentialsProvider(buildSnsCredentialsProvider())
        .build();
  }

  @Bean
  public S3Client s3Client() {
    return apply(amazonProperties.getS3().getEndpoint(), S3Client.builder())
        .region(Region.of(amazonProperties.getS3().getRegion()))
        .credentialsProvider(buildS3CredentialsProvider())
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

  private AwsCredentialsProvider buildSnsCredentialsProvider() {
    if (allNotNull(amazonProperties.getSns().getAccessKeyId(), amazonProperties.getSns().getSecretKey())) {
      return () -> AwsBasicCredentials.create(
          amazonProperties.getSns().getAccessKeyId(),
          amazonProperties.getSns().getSecretKey()
      );
    } else {
      return DefaultCredentialsProvider.create();
    }
  }

  private AwsCredentialsProvider buildS3CredentialsProvider() {
    if (allNotNull(amazonProperties.getS3().getAccessKeyId(), amazonProperties.getS3().getSecretKey())) {
      return () -> AwsBasicCredentials.create(
          amazonProperties.getS3().getAccessKeyId(),
          amazonProperties.getS3().getSecretKey()
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
