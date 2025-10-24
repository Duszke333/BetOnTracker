package org.betonskm.orchestrator.application.port.out;

import java.util.UUID;

public interface CategoryWebsiteRepository {

  void link(Integer categoryId, UUID websiteId);
}
