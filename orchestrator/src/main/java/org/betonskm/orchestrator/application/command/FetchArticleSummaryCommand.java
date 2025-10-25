package org.betonskm.orchestrator.application.command;

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
public class FetchArticleSummaryCommand {

  private UUID articleUrl;

  public static FetchArticleSummaryCommand from(String articleUrl) {
    return FetchArticleSummaryCommand.builder()
        .articleUrl(UUID.fromString(articleUrl))
        .build();
  }
}
