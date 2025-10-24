package org.betonskm.orchestrator.application.port.out;

import java.util.List;
import java.util.Optional;
import org.betonskm.orchestrator.domain.category.Category;

public interface CategoryRepository {

  Category save(Category category);

  List<Category> fetchAllActiveCategories();

  Optional<Category> fetchCategoryById(Integer id);
}
