package org.betonskm.orchestrator.application.port.in;

import org.betonskm.orchestrator.application.command.CreateCategoryCommand;
import org.betonskm.orchestrator.domain.category.Category;

public interface CategoryManagementUseCase {

  Category createCategory(CreateCategoryCommand command);
}
