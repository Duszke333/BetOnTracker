package org.betonskm.orchestrator.adapter.api.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class FetchArticlesAPIResponse {

  @Schema(
      requiredMode = Schema.RequiredMode.REQUIRED,
      description = "List of articles fetched",
      example = "[{...}, {...}]"
  )
  private List<ArticleResponse> articles;

  @Getter
  @Setter
  @ToString
  @AllArgsConstructor
  @NoArgsConstructor
  public static class ArticleResponse {

    @Schema(
        requiredMode = Schema.RequiredMode.REQUIRED,
        description = "Unique identifier for the article",
        example = "3fa85f64-5717-4562-b3fc-2c963f66afa6"
    )
    private UUID id;

    @Schema(
        requiredMode = Schema.RequiredMode.REQUIRED,
        description = "Title of the article",
        example = "Breaking News: Major Event Unfolds"
    )
    private String title;

    @Schema(
        requiredMode = Schema.RequiredMode.REQUIRED,
        description = "URL of the article",
        example = "https://www.example.com/articles/major-event"
    )
    private String articleUrl;

    @Schema(
        requiredMode = Schema.RequiredMode.REQUIRED,
        description = "One line summary of the article",
        example = "A major event has unfolded, impacting many."
    )
    private String oneLineSummary;

    @Schema(
        requiredMode = Schema.RequiredMode.REQUIRED,
        description = "List of keywords associated with the article",
        example = "[\"event\", \"breaking news\", \"impact\"]"
    )
    private List<String> keywords;

    @Schema(
        requiredMode = Schema.RequiredMode.REQUIRED,
        description = "Importance of the article in regards to the category (1-5)",
        example = "3"
    )
    private Short importanceScore;

    @Schema(
        requiredMode = Schema.RequiredMode.REQUIRED,
        description = "Sentiment of the article (1-5)",
        example = "2"
    )
    private Short sentimentScore;

    @Schema(
        requiredMode = Schema.RequiredMode.REQUIRED,
        description = "Reliability of the article source (1-5)",
        example = "4"
    )
    private Short sourceReliabilityScore;
  }
}
