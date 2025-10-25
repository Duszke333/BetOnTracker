package org.betonskm.orchestrator.domain.article;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.betonskm.orchestrator.adapter.event.listener.summary.model.ArticleSummaryEvent;

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
  private String title;
  private OffsetDateTime createdAt;
  private String oneLineSummary;
  private List<String> keywords;
  private Short importanceScore;
  private Short sentimentScore;
  private Short sourceReliabilityScore;

  public void update(ArticleSummaryEvent event) {
    this.oneLineSummary = event.getOneLineSummary();
    this.keywords = event.getKeywords();
    this.importanceScore = event.getImportanceScore();
    this.sentimentScore = event.getSentimentScore();
    this.sourceReliabilityScore = event.getSourceReliabilityScore();
    this.s3SummaryPath = event.getArticleSummaryPath();
  }
}
