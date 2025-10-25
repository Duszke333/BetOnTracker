package org.betonskm.orchestrator.adapter.api.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import java.time.OffsetDateTime;
import java.util.List;
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
public class FetchWebsitesAPIResponse {

  @Schema(
      requiredMode = RequiredMode.REQUIRED,
      description = "List of fetched websites with their metadata",
      example = "[{\"url\": \"https://example.com\", \"etag\": \"W/\\\"123456789\\\"\", \"lastFetchedAt\": \"2024-06-01T12:00:00Z\"}]"
  )
  private List<WebsiteResponse> websites;

  @Getter
  @Setter
  @ToString
  @AllArgsConstructor
  @NoArgsConstructor
  public static class WebsiteResponse {

    @Schema(
        requiredMode = RequiredMode.REQUIRED,
        description = "The URL of the website",
        example = "https://example.com"
    )
    private String url;

    @Schema(
        requiredMode = RequiredMode.REQUIRED,
        description = "The ETag of the fetched website",
        example = "W/\"123456789\""
    )
    private String etag;

    @Schema(
        requiredMode = RequiredMode.REQUIRED,
        description = "The timestamp when the website was last fetched",
        example = "2024-06-01T12:00:00Z"
    )
    private OffsetDateTime lastFetchedAt;
  }
}
