package org.betonskm.orchestrator.application.port.out;

import org.betonskm.orchestrator.domain.article.Article;

public interface ArticleRepository {

  Article save(Article article);
}
