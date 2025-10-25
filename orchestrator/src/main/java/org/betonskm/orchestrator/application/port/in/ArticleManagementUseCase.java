package org.betonskm.orchestrator.application.port.in;

import org.betonskm.orchestrator.adapter.event.listener.news.model.NewsArticleEvent;
import org.betonskm.orchestrator.adapter.event.listener.summary.model.ArticleSummaryEvent;

public interface ArticleManagementUseCase {

  void createArticle(NewsArticleEvent event);

  void updateArticle(ArticleSummaryEvent event);
}
