package org.betonskm.orchestrator.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.betonskm.orchestrator.adapter.event.listener.news.model.NewsArticleEvent;
import org.betonskm.orchestrator.adapter.event.publisher.rawArticles.RawArticlesPublisher;
import org.betonskm.orchestrator.application.command.PublishRawArticleCommand;
import org.betonskm.orchestrator.application.port.in.ArticleManagementUseCase;
import org.betonskm.orchestrator.application.port.out.ArticleRepository;
import org.betonskm.orchestrator.application.port.out.CategoryRepository;
import org.betonskm.orchestrator.application.port.out.CategoryWebsiteRepository;
import org.betonskm.orchestrator.application.port.out.WebsiteRepository;
import org.betonskm.orchestrator.configuration.exception.OrchestratorException;
import org.betonskm.orchestrator.domain.article.Article;
import org.betonskm.orchestrator.domain.website.Website;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArticleManagementService implements ArticleManagementUseCase {

  private final WebsiteRepository websiteRepository;
  private final ArticleRepository articleRepository;
  private final CategoryRepository categoryRepository;
  private final CategoryWebsiteRepository categoryWebsiteRepository;
  private final RawArticlesPublisher publisher;

  @Override
  @Transactional
  public void createArticle(NewsArticleEvent event) {

    Website website = websiteRepository.fetchByUrl(event.getFeedUrl())
        .orElseThrow(() -> new OrchestratorException(
            "Website not found with id: " + event.getFeedUrl()));

    categoryWebsiteRepository.fetchCategoryIdsByWebsiteId(website.getId()).forEach(categoryId -> {
      createArticleAndSend(event, categoryId);
    });
  }

  private void createArticleAndSend(NewsArticleEvent event, Integer categoryId) {
    Article article = Article.builder()
        .categoryId(categoryId)
        .articleLink(event.getArticleUrl())
        .s3ArticleContentPath(event.getS3path())
        .title(event.getTitle())
        .build();

    Article savedArticle = articleRepository.save(article);

    log.info("Article created with id: {} for category id: {}", savedArticle.getId(), categoryId);

    String categoryName = categoryRepository.fetchCategoryNameById(categoryId)
        .orElseThrow(() -> new OrchestratorException("Category not found with id: " + categoryId));

    publisher.publishEvent(PublishRawArticleCommand.from(savedArticle, categoryName));
  }
}
