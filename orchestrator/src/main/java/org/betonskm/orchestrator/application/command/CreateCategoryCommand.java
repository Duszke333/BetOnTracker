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

  private String userId;
  private String categoryName;

  public static CreateCategoryCommand from(String userId, String categoryName) {
    return CreateCategoryCommand.builder()
        .userId(userId)
        .categoryName(categoryName)
        .build();
  }
}
