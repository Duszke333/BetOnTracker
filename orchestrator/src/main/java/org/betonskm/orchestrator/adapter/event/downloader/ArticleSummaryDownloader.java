package org.betonskm.orchestrator.adapter.event.downloader;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.betonskm.orchestrator.configuration.aws.AmazonProperties;
import org.betonskm.orchestrator.domain.summary.ArticleSummary;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;

@Service
@RequiredArgsConstructor
public class ArticleSummaryDownloader {

  private final S3Client s3Client;
  private final AmazonProperties amazonProperties;
  private final ObjectMapper objectMapper;

  public ArticleSummary downloadSummary(String key) throws IOException {
    GetObjectRequest getObjectRequest = GetObjectRequest.builder()
        .bucket(amazonProperties.getS3().getBucket())
        .key(key)
        .build();

    // Download object as bytes
    ResponseBytes<?> objectBytes = s3Client.getObjectAsBytes(getObjectRequest);

    // Deserialize JSON directly into ArticleSummary
    return objectMapper.readValue(objectBytes.asByteArray(), ArticleSummary.class);
  }
}
