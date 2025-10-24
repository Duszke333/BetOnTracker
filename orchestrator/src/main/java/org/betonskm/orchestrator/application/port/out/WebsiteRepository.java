package org.betonskm.orchestrator.application.port.out;

import org.betonskm.orchestrator.domain.website.Website;

public interface WebsiteRepository {

  Website save(Website website);
}
