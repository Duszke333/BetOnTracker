package org.betonskm.orchestrator.application.port.in;

import java.util.List;
import org.betonskm.orchestrator.application.command.AddWebsiteToCategoryCommand;
import org.betonskm.orchestrator.application.command.CreateCategoryCommand;
import org.betonskm.orchestrator.application.command.DecommissionCategoryCommand;
import org.betonskm.orchestrator.domain.category.Category;
import org.betonskm.orchestrator.domain.website.Website;

public interface CategoryManagementUseCase {

  Category createCategory(CreateCategoryCommand command);

  List<Category> fetchAllActiveCategories();

  void decommissionCategory(DecommissionCategoryCommand command);

  Website addWebsiteToCategory(AddWebsiteToCategoryCommand command);
}
