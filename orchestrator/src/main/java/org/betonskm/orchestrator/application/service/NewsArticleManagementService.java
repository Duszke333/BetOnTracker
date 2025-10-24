package org.betonskm.orchestrator.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.betonskm.orchestrator.adapter.event.listener.news.model.NewsArticleEvent;
import org.betonskm.orchestrator.application.port.in.NewsArticleManagementUseCase;
import org.betonskm.orchestrator.application.port.out.WebsiteRepository;
import org.betonskm.orchestrator.configuration.exception.OrchestratorException;
import org.betonskm.orchestrator.domain.website.Website;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class NewsArticleManagementService implements NewsArticleManagementUseCase {

  private final WebsiteRepository websiteRepository;

  @Override
  @Transactional
  @Modifying
  public void updateWebsite(NewsArticleEvent event) {
    Website website = websiteRepository.fetchByUrl(event.getUrl())
        .orElseThrow(() -> new OrchestratorException("No website found for URL: " + event.getUrl()));

    website.update(event);
    websiteRepository.save(website);
    log.info("Updated article for website URL: {}", event.getUrl());
  }
}
