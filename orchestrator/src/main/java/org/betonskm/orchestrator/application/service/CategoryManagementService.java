package org.betonskm.orchestrator.application.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.betonskm.orchestrator.application.command.AddWebsiteToCategoryCommand;
import org.betonskm.orchestrator.application.command.CreateCategoryCommand;
import org.betonskm.orchestrator.application.command.DecommissionCategoryCommand;
import org.betonskm.orchestrator.application.port.in.CategoryManagementUseCase;
import org.betonskm.orchestrator.application.port.out.CategoryRepository;
import org.betonskm.orchestrator.application.port.out.CategoryWebsiteRepository;
import org.betonskm.orchestrator.application.port.out.WebsiteRepository;
import org.betonskm.orchestrator.configuration.TimeProvider;
import org.betonskm.orchestrator.configuration.exception.OrchestratorException;
import org.betonskm.orchestrator.domain.category.Category;
import org.betonskm.orchestrator.domain.website.Website;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryManagementService implements CategoryManagementUseCase {

  private final CategoryRepository categoryRepository;
  private final TimeProvider timeProvider;
  private final WebsiteRepository websiteRepository;
  private final CategoryWebsiteRepository categoryWebsiteRepository;


  @Override
  @Transactional
  public Category createCategory(CreateCategoryCommand command) {

    Category category = Category.builder()
        .name(command.getCategoryName())
        .build();

    Category savedCategory = categoryRepository.save(category);
    log.info("[CATEGORY MANAGEMENT] Created new category: {}", savedCategory);
    return savedCategory;
  }

  @Override
  @Transactional(readOnly = true)
  public List<Category> fetchAllActiveCategories() {
    return categoryRepository.fetchAllActiveCategories();
  }

  @Override
  @Transactional
  public void decommissionCategory(DecommissionCategoryCommand command) {
    Category category = categoryRepository.fetchCategoryById(command.getCategoryId())
        .orElseThrow(() -> new OrchestratorException("Category with ID " + command.getCategoryId() + " not found"));

    if (Objects.nonNull(category.getDecommissionedAt())) {
      log.info("[CATEGORY MANAGEMENT] Category with ID {} is already decommissioned", command.getCategoryId());
      return;
    }

    category.setDecommissionedAt(timeProvider.now());
    categoryRepository.save(category);
    log.info("[CATEGORY MANAGEMENT] Decommissioned category: {}", category);
  }

  @Override
  @Transactional
  @Modifying
  public Website addWebsiteToCategory(AddWebsiteToCategoryCommand command) {

    if (categoryRepository.fetchCategoryById(command.getCategoryId()).isEmpty()) {
      throw new OrchestratorException("Category with ID " + command.getCategoryId() + " not found");
    }

    Optional<Website> optionalWebsite = websiteRepository.fetchByUrl(command.getWebsiteUrl());
    if (optionalWebsite.isPresent()) {
      Website website = optionalWebsite.get();
      log.info("[CATEGORY MANAGEMENT] Website with URL {} already exists: {}", command.getWebsiteUrl(), website);
      if (categoryWebsiteRepository.link(command.getCategoryId(), website.getId())) {
        log.info("[CATEGORY MANAGEMENT] Website with URL {} is already linked to category ID {}", command.getWebsiteUrl(), command.getCategoryId());
        return website;
      }
      website.incrementReferenceCount();
      return websiteRepository.save(website);
    }

    Website website = Website.builder()
        .url(command.getWebsiteUrl())
        .build();
    Website savedWebsite = websiteRepository.save(website);
    log.info("[CATEGORY MANAGEMENT] Created new website: {}", savedWebsite);

    categoryWebsiteRepository.link(command.getCategoryId(), savedWebsite.getId());
    log.info("[CATEGORY MANAGEMENT] Added website to category: {}", savedWebsite);
    return savedWebsite;
  }
}
