package org.betonskm.orchestrator.adapter.db.category;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.betonskm.orchestrator.application.port.out.CategoryRepository;
import org.betonskm.orchestrator.domain.category.Category;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CategoryRepositoryAdapter implements CategoryRepository {

  private final CategoryEntityRepository repository;
  private final CategoryMapper mapper;

  @Override
  public Category save(Category category) {
    return mapper.fromEntity(repository.save(mapper.toEntity(category)));
  }
}
