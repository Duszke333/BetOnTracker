package org.betonskm.orchestrator.adapter.api.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AddWebsiteToFeedAPIRequest {

  private String websiteUrl;
}
