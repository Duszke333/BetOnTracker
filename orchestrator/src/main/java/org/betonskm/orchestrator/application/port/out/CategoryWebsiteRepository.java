package org.betonskm.orchestrator.application.port.out;

import java.util.UUID;
import org.betonskm.orchestrator.domain.category.Category;
import org.betonskm.orchestrator.domain.website.Website;

public interface CategoryWebsiteRepository {

  boolean link(Integer categoryId, UUID websiteId);
}
