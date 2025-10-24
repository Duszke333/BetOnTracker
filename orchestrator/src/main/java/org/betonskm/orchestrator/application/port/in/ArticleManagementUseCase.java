package org.betonskm.orchestrator.application.port.in;

import org.betonskm.orchestrator.adapter.event.listener.news.model.NewsArticleEvent;

public interface ArticleManagementUseCase {

  void createArticle(NewsArticleEvent event);
}
