package org.betonskm.orchestrator.adapter.event.publisher.rawArticles.model;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RawArticleDto {

  private UUID articleId;
  private String articlePath;
  private String category;
}
