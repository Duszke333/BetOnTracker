package org.betonskm.orchestrator.adapter.api.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.betonskm.orchestrator.domain.summary.ArticleSummary;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ArticleSummaryAPIResponse {

  @Schema(
      requiredMode = Schema.RequiredMode.REQUIRED,
      description = "Unique identifier of the article",
      example = "3fa85f64-5717-4562-b3fc-2c963f66afa6"
  )
  private UUID articleId;

  @Schema(
      requiredMode = Schema.RequiredMode.REQUIRED,
      description = "Summary of the article",
      example = "This article discusses the impact of climate change on global ecosystems..."
  )
  private String summary;

  public static ArticleSummaryAPIResponse from(UUID articleId, ArticleSummary summary) {
    return new ArticleSummaryAPIResponse(articleId, summary.getSummary());
  }
}
