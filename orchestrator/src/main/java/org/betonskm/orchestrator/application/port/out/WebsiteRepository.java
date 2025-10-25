package org.betonskm.orchestrator.application.port.out;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.betonskm.orchestrator.domain.website.Website;

public interface WebsiteRepository {

  Website save(Website website);
  Optional<Website> fetchByUrl(String url);

  List<Website> fetchActiveWebsites();

  Optional<Website> fetchById(UUID websiteId);

  List<Website> fetchWebsitesForCategory(Integer categoryId);
}
