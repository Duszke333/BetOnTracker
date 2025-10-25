package org.betonskm.orchestrator.adapter.event.listener.summary.model;

import java.util.List;
import java.util.UUID;
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
public class ArticleSummaryEvent {

  private UUID articleId;
  private String articleSummaryPath;
  private String oneLineSummary;
  private List<String> keywords;
  private Short importanceScore;
  private Short sentimentScore;
  private Short sourceReliabilityScore;
}
