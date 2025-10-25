package org.betonskm.orchestrator.adapter.api.model.response;

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
public class WebsiteAPIResponse {

  private UUID id;
  private String url;
}
