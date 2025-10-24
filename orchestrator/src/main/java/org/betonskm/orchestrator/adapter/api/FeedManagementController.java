package org.betonskm.orchestrator.adapter.api;

import static org.betonskm.orchestrator.configuration.Constants.FEED_MANAGEMENT_PREFIX;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.betonskm.orchestrator.adapter.api.model.mapper.CategoryResponseMapper;
import org.betonskm.orchestrator.adapter.api.model.request.CreateCategoryAPIRequest;
import org.betonskm.orchestrator.adapter.api.model.response.CategoryAPIResponse;
import org.betonskm.orchestrator.adapter.api.model.response.FetchAllCategoriesAPIResponse;
import org.betonskm.orchestrator.application.command.CreateCategoryCommand;
import org.betonskm.orchestrator.application.port.in.CategoryManagementUseCase;
import org.betonskm.orchestrator.configuration.annotations.DefaultApiSecurity;
import org.betonskm.orchestrator.domain.category.Category;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "Feed Management", description = "Controller for managing feeds")
public class FeedManagementController {

  private static final String CREATE_CATEGORY_PATH = FEED_MANAGEMENT_PREFIX + "/categories/create";
  private static final String FETCH_CATEGORIES_PATH = FEED_MANAGEMENT_PREFIX + "/categories/fetch";

  private final CategoryManagementUseCase categoryManagementUseCase;
  private final CategoryResponseMapper categoryResponseMapper;

  @PostMapping(path = CREATE_CATEGORY_PATH, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
  @DefaultApiSecurity
  @Operation(summary = "Create a new feed category")
  @ApiResponse(responseCode = "200", description = "Feed category created successfully")
  public CategoryAPIResponse createCategory(@RequestBody @Valid CreateCategoryAPIRequest createCategoryAPIRequest) {
    log.info("[CREATE CATEGORY] Request received: {}", createCategoryAPIRequest);

    CreateCategoryCommand command = CreateCategoryCommand.from(createCategoryAPIRequest.getCategoryName());
    Category createdCategory = categoryManagementUseCase.createCategory(command);

    return categoryResponseMapper.toAPIResponse(createdCategory);
  }

  @GetMapping(path = FETCH_CATEGORIES_PATH, produces = APPLICATION_JSON_VALUE)
  @DefaultApiSecurity
  @Operation(summary = "Fetch all active feed categories")
  @ApiResponse(responseCode = "200", description = "Feed categories fetched successfully")
  public FetchAllCategoriesAPIResponse fetchAllCategories() {
    log.info("[FETCH CATEGORIES] Request received");

    List<Category> activeCategories = categoryManagementUseCase.fetchAllActiveCategories();

    log.info("[FETCH CATEGORIES] Fetched {} active categories: {}", activeCategories.size(), activeCategories);

    return categoryResponseMapper.toFetchAllAPIResponse(activeCategories);
  }
}
