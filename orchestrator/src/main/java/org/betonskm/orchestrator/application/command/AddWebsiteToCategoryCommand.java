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
public class AddWebsiteToCategoryCommand {

  private Integer categoryId;
  private String websiteUrl;

  public static AddWebsiteToCategoryCommand from(Integer categoryId, String websiteUrl) {
    return AddWebsiteToCategoryCommand.builder()
        .categoryId(categoryId)
        .websiteUrl(websiteUrl)
        .build();
  }
}
