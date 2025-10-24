package org.betonskm.orchestrator.application.port.out;

import java.util.Optional;
import org.betonskm.orchestrator.domain.website.Website;

public interface WebsiteRepository {

  Website save(Website website);
  Optional<Website> fetchByUrl(String url);
}
