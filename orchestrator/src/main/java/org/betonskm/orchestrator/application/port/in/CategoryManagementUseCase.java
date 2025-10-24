package org.betonskm.orchestrator.application.port.in;

import java.util.List;
import org.betonskm.orchestrator.application.command.CreateCategoryCommand;
import org.betonskm.orchestrator.domain.category.Category;

public interface CategoryManagementUseCase {

  Category createCategory(CreateCategoryCommand command);

  List<Category> fetchAllActiveCategories();
}
