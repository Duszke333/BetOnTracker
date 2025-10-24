package org.betonskm.orchestrator.application.service;

import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.betonskm.orchestrator.application.command.CreateCategoryCommand;
import org.betonskm.orchestrator.application.command.DecommissionCategoryCommand;
import org.betonskm.orchestrator.application.port.in.CategoryManagementUseCase;
import org.betonskm.orchestrator.application.port.out.CategoryRepository;
import org.betonskm.orchestrator.configuration.TimeProvider;
import org.betonskm.orchestrator.configuration.exception.OrchestratorException;
import org.betonskm.orchestrator.domain.category.Category;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryManagementService implements CategoryManagementUseCase {

  private final CategoryRepository categoryRepository;
  private final TimeProvider timeProvider;


  @Override
  public Category createCategory(CreateCategoryCommand command) {

    Category category = Category.builder()
        .name(command.getCategoryName())
        .build();

    Category savedCategory = categoryRepository.save(category);
    log.info("[CATEGORY MANAGEMENT] Created new category: {}", savedCategory);
    return savedCategory;
  }

  @Override
  public List<Category> fetchAllActiveCategories() {
    return categoryRepository.fetchAllActiveCategories();
  }

  @Override
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
}
