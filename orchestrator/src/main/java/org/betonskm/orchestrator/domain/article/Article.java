package org.betonskm.orchestrator.domain.article;

import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Article {

  private UUID id;
  private Integer categoryId;
  private String articleLink;
  private String s3ArticleContentPath;
  private String s3SummaryPath;
  private OffsetDateTime createdAt;
}
