package org.betonskm.orchestrator.adapter.api;

import static org.betonskm.orchestrator.configuration.Costants.FEED_MANAGEMENT_PREFIX;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.betonskm.orchestrator.adapter.api.model.request.CreateCategoryAPIRequest;
import org.betonskm.orchestrator.configuration.annotations.DefaultApiSecurity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "Feed Management", description = "Controller for managing feeds")
public class FeedManagementController {

  private static final String CREATE_FEED_PATH = FEED_MANAGEMENT_PREFIX + "/create";

  @PostMapping(path = CREATE_FEED_PATH, consumes = APPLICATION_JSON_VALUE)
  @DefaultApiSecurity
  @ApiResponse(responseCode = "201", description = "Feed category created successfully")
  public void createCategory(@RequestBody @Valid CreateCategoryAPIRequest createCategoryAPIRequest) {
    log.info("[CREATE CATEGORY] Request received: {}", createCategoryAPIRequest);
  }
}
