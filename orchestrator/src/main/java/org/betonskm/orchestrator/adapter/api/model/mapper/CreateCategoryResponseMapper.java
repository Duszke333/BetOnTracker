package org.betonskm.orchestrator.adapter.api.model.mapper;

import org.betonskm.orchestrator.adapter.api.model.response.CreateCategoryAPIResponse;
import org.betonskm.orchestrator.configuration.MapperConfiguration;
import org.betonskm.orchestrator.domain.category.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfiguration.class)
public interface CreateCategoryResponseMapper {

  @Mapping(target = "categoryId", source = "id")
  @Mapping(target = "categoryName", source = "name")
  CreateCategoryAPIResponse toAPIResponse(Category category);
}
