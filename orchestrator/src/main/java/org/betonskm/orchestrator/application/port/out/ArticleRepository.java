package org.betonskm.orchestrator.application.port.out;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.betonskm.orchestrator.domain.article.Article;

public interface ArticleRepository {

  Article save(Article article);

  Optional<Article> fetchById(UUID articleId);

  List<Article> fetchArticlesForCategory(Integer categoryId);
}
