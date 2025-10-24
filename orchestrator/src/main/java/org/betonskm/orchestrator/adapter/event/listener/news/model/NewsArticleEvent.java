package org.betonskm.orchestrator.adapter.event.listener.news.model;

import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class NewsArticleEvent {

  private String feedUrl;
  private String articleUrl;
  private String s3path;
  private String etag;
  private OffsetDateTime fetchedAt;
}
