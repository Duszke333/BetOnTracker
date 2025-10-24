package org.betonskm.orchestrator.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.betonskm.orchestrator.application.command.CreateCategoryCommand;
import org.betonskm.orchestrator.application.port.in.CategoryManagementUseCase;
import org.betonskm.orchestrator.application.port.out.CategoryRepository;
import org.betonskm.orchestrator.domain.category.Category;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryManagementService implements CategoryManagementUseCase {

  private final CategoryRepository categoryRepository;


  @Override
  public Category createCategory(CreateCategoryCommand command) {

    Category category = Category.builder()
        .name(command.getCategoryName())
        .build();

    Category savedCategory = categoryRepository.save(category);
    log.info("[CATEGORY MANAGEMENT] Created new category: {}", savedCategory);
    return savedCategory;
  }
}
