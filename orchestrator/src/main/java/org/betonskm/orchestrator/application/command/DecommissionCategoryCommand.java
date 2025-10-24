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
public class DecommissionCategoryCommand {

  private Integer categoryId;

  public static DecommissionCategoryCommand from(Integer categoryId) {
    return DecommissionCategoryCommand.builder()
        .categoryId(categoryId)
        .build();
  }
}
