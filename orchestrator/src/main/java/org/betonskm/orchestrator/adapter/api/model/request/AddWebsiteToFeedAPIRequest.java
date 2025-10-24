package org.betonskm.orchestrator.adapter.api.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AddWebsiteToFeedAPIRequest {

  @Schema(
      requiredMode = Schema.RequiredMode.REQUIRED,
      description = "The URL of the website to be added to the feed",
      example = "https://www.example.com"
  )
  private String websiteUrl;
}
