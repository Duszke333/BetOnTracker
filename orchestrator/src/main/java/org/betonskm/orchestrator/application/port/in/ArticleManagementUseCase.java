package org.betonskm.orchestrator.application.port.in;

import java.util.List;
import org.betonskm.orchestrator.adapter.event.listener.news.model.NewsArticleEvent;
import org.betonskm.orchestrator.adapter.event.listener.summary.model.ArticleSummaryEvent;
import org.betonskm.orchestrator.application.command.FetchArticlesCommand;
import org.betonskm.orchestrator.domain.article.Article;

public interface ArticleManagementUseCase {

  void createArticle(NewsArticleEvent event);

  void updateArticle(ArticleSummaryEvent event);

  List<Article> fetchArticles(FetchArticlesCommand command);
}
