package org.betonskm.orchestrator.adapter.api.model.mapper;

import org.betonskm.orchestrator.adapter.api.model.response.CategoryAPIResponse;
import org.betonskm.orchestrator.configuration.MapperConfiguration;
import org.betonskm.orchestrator.domain.category.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfiguration.class)
public interface CategoryResponseMapper {

  @Mapping(target = "categoryId", source = "id")
  @Mapping(target = "categoryName", source = "name")
  CategoryAPIResponse toAPIResponse(Category category);
}
