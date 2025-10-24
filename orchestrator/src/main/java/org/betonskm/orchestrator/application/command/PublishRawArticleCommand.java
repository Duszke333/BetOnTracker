package org.betonskm.orchestrator.application.command;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.betonskm.orchestrator.domain.article.Article;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PublishRawArticleCommand {

  private UUID articleId;
  private String articlePath;
  private String category;

  public static PublishRawArticleCommand from(Article article, String category) {
    return PublishRawArticleCommand.builder()
        .articleId(article.getId())
        .articlePath(article.getS3ArticleContentPath())
        .category(category)
        .build();
  }
}
