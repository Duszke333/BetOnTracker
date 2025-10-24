package org.betonskm.orchestrator.application.port.out;

import org.betonskm.orchestrator.domain.category.Category;
import org.betonskm.orchestrator.domain.website.Website;

public interface CategoryWebsiteRepository {

  boolean link(Category category, Website website);
}
