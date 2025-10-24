package org.betonskm.orchestrator.application.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.betonskm.orchestrator.application.port.in.WebsiteTrackingUseCase;
import org.betonskm.orchestrator.application.port.out.WebsiteRepository;
import org.betonskm.orchestrator.domain.website.Website;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class WebsiteTrackingService implements WebsiteTrackingUseCase {

  private final WebsiteRepository websiteRepository;

  @Override
  @Transactional(readOnly = true)
  public List<Website> fetchWebsites() {
    return websiteRepository.fetchActiveWebsites();
  }
}
