package org.betonskm.orchestrator.application.command;

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
public class FetchArticlesCommand {

  private Integer categoryId;

  public static FetchArticlesCommand from(Integer categoryId) {
    return FetchArticlesCommand.builder()
        .categoryId(categoryId)
        .build();
  }
}
