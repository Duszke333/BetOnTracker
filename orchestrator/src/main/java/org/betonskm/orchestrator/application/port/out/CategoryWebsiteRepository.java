package org.betonskm.orchestrator.application.port.out;

import java.util.List;
import java.util.UUID;

public interface CategoryWebsiteRepository {

  boolean link(Integer categoryId, UUID websiteId);

  List<Integer> fetchCategoryIdsByWebsiteId(UUID websiteId);

  List<UUID> fetchWebsiteIdsByCategoryId(Integer categoryId);

  void deleteAllLinksForCategory(Integer categoryId);
}
