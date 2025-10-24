package org.betonskm.orchestrator.application.port.out;

import org.betonskm.orchestrator.domain.category.Category;

public interface CategoryRepository {

  Category save(Category category);
}
