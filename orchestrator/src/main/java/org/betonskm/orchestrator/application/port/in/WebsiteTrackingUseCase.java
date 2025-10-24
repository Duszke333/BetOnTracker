package org.betonskm.orchestrator.application.port.in;

import java.util.List;
import org.betonskm.orchestrator.domain.website.Website;

public interface WebsiteTrackingUseCase {

  List<Website> fetchWebsites();
}
