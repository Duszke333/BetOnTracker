package org.betonskm.orchestrator.adapter.db.category;

import org.betonskm.orchestrator.configuration.MapperConfiguration;
import org.betonskm.orchestrator.domain.category.Category;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfiguration.class)
public interface CategoryMapper {

  Category fromEntity(CategoryEntity entity);

  CategoryEntity toEntity(Category category);
}
