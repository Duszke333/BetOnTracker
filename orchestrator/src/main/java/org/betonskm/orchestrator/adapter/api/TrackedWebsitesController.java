package org.betonskm.orchestrator.adapter.api;

import static org.betonskm.orchestrator.configuration.Constants.WEBSITES_PREFIX;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.betonskm.orchestrator.adapter.api.model.mapper.WebsiteResponseMapper;
import org.betonskm.orchestrator.adapter.api.model.response.FetchWebsitesAPIResponse;
import org.betonskm.orchestrator.application.port.in.WebsiteTrackingUseCase;
import org.betonskm.orchestrator.configuration.annotations.DefaultApiSecurity;
import org.betonskm.orchestrator.domain.website.Website;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "Website tracking", description = "Controller for website tracking operations")
public class TrackedWebsitesController {

  private static final String FETCH_TRACKED_WEBSITES_PATH = WEBSITES_PREFIX + "/fetch";

  private final WebsiteTrackingUseCase websiteTrackingUseCase;
  private final WebsiteResponseMapper websiteResponseMapper;

  @GetMapping(path = FETCH_TRACKED_WEBSITES_PATH, produces = APPLICATION_JSON_VALUE)
  @DefaultApiSecurity
  @Operation(summary = "Fetch all active websites (reference count > 0)")
  @ApiResponse(responseCode = "200", description = "Websites fetched successfully")
  public FetchWebsitesAPIResponse fetchTrackedWebsites() {
    // Implementation goes here
    log.info("[FETCH WEBSITES] Fetching tracked websites");

    List<Website> websites = websiteTrackingUseCase.fetchWebsites();
    log.info("[FETCH WEBSITES] Fetched {} tracked websites", websites.size());
    return websiteResponseMapper.toFetchWebsitesAPIResponse(websites);
  }
}
