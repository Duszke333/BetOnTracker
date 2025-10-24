package org.betonskm.orchestrator.application.port.in;

import org.betonskm.orchestrator.adapter.event.listener.news.model.NewsArticleEvent;

public interface NewsArticleManagementUseCase {

  void updateWebsite(NewsArticleEvent event);
}
