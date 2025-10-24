package org.betonskm.orchestrator.adapter.api;

import static org.betonskm.orchestrator.configuration.Costants.FEED_MANAGEMENT_PREFIX;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.betonskm.orchestrator.adapter.api.model.mapper.CategoryResponseMapper;
import org.betonskm.orchestrator.adapter.api.model.mapper.WebsiteResponseMapper;
import org.betonskm.orchestrator.adapter.api.model.request.AddWebsiteToFeedAPIRequest;
import org.betonskm.orchestrator.adapter.api.model.request.CreateCategoryAPIRequest;
import org.betonskm.orchestrator.adapter.api.model.response.CategoryAPIResponse;
import org.betonskm.orchestrator.adapter.api.model.response.FetchAllCategoriesAPIResponse;
import org.betonskm.orchestrator.adapter.api.model.response.WebsiteAPIResponse;
import org.betonskm.orchestrator.application.command.AddWebsiteToCategoryCommand;
import org.betonskm.orchestrator.application.command.CreateCategoryCommand;
import org.betonskm.orchestrator.application.command.DecommissionCategoryCommand;
import org.betonskm.orchestrator.application.port.in.CategoryManagementUseCase;
import org.betonskm.orchestrator.configuration.annotations.DefaultApiSecurity;
import org.betonskm.orchestrator.domain.category.Category;
import org.betonskm.orchestrator.domain.website.Website;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "Category Management", description = "Controller for managing categories")
public class CategoryManagementController {

  private static final String CREATE_CATEGORY_PATH = FEED_MANAGEMENT_PREFIX + "/categories/create";
  private static final String FETCH_CATEGORIES_PATH = FEED_MANAGEMENT_PREFIX + "/categories/fetch";
  private static final String DECOMISSION_CATEGORY_PATH = FEED_MANAGEMENT_PREFIX + "/categories/{categoryId}/delete";
  private static final String ADD_WEBSITE_TO_FEED_PATH = FEED_MANAGEMENT_PREFIX + "/categories/{categoryId}/add";

  private final CategoryManagementUseCase categoryManagementUseCase;
  private final CategoryResponseMapper categoryResponseMapper;
  private final WebsiteResponseMapper websiteResponseMapper;

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

  @DeleteMapping(path = DECOMISSION_CATEGORY_PATH)
  @DefaultApiSecurity
  @Operation(summary = "Delete a feed category")
  @ApiResponse(responseCode = "200", description = "Feed category deleted successfully")
  public void decommissionCategory(
      @Parameter(description = "ID of the category to delete", required = true, example = "1")
      @PathVariable("categoryId") @Positive Integer categoryId
  ) {
    log.info("[DECOMISSION CATEGORY] Request received for categoryId: {}", categoryId);

    DecommissionCategoryCommand command = DecommissionCategoryCommand.from(categoryId);
    categoryManagementUseCase.decommissionCategory(command);
  }

  @PostMapping(path = ADD_WEBSITE_TO_FEED_PATH, consumes = APPLICATION_JSON_VALUE)
  public WebsiteAPIResponse addWebsiteToFeed(
      @Parameter(description = "ID of the category to which the website will be added", required = true, example = "1")
      @PathVariable("categoryId") @Positive Integer categoryId,
      @RequestBody @Valid AddWebsiteToFeedAPIRequest request
  ) {
    log.info("[ADD WEBSITE TO FEED] Request received for categoryId: {} to add website: {}", categoryId, request);

    AddWebsiteToCategoryCommand command = AddWebsiteToCategoryCommand.from(categoryId, request.getWebsiteUrl());
    // Implementation goes here

    Website addedWebsite = categoryManagementUseCase.addWebsiteToCategory(command);

    return websiteResponseMapper.toAPIResponse(addedWebsite);
  }
}
