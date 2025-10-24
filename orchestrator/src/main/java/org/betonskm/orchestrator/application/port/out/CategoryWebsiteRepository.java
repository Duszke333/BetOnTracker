package org.betonskm.orchestrator.application.port.out;

import java.util.List;
import java.util.UUID;
import org.betonskm.orchestrator.domain.category.Category;
import org.betonskm.orchestrator.domain.website.Website;

public interface CategoryWebsiteRepository {

  boolean link(Integer categoryId, UUID websiteId);

  List<Integer> fetchCategoryIdsByWebsiteId(UUID websiteId);
}
