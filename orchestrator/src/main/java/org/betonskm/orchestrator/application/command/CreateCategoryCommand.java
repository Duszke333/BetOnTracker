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
public class CreateCategoryCommand {

  private String categoryName;

  public static CreateCategoryCommand from(String categoryName) {
    return CreateCategoryCommand.builder()
        .categoryName(categoryName)
        .build();
  }
}
