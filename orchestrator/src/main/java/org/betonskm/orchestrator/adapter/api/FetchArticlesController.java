package org.betonskm.orchestrator.adapter.api;


import static org.betonskm.orchestrator.configuration.Constants.ARTICLES_PREFIX;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.betonskm.orchestrator.adapter.api.model.mapper.FetchArticlesMapper;
import org.betonskm.orchestrator.adapter.api.model.response.FetchArticlesAPIResponse;
import org.betonskm.orchestrator.application.command.FetchArticlesCommand;
import org.betonskm.orchestrator.application.port.in.ArticleManagementUseCase;
import org.betonskm.orchestrator.configuration.annotations.DefaultApiSecurity;
import org.betonskm.orchestrator.domain.article.Article;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "Fetch Articles", description = "Controller for fetching articles for given categories")
public class FetchArticlesController {

  private final static String FETCH_ARTICLES_PATH = ARTICLES_PREFIX + "/fetch";

  private final ArticleManagementUseCase articleManagementUseCase;
  private final FetchArticlesMapper fetchArticlesMapper;

  @GetMapping(path = FETCH_ARTICLES_PATH, produces = APPLICATION_JSON_VALUE)
  @DefaultApiSecurity
  @Operation(summary = "Fetch all articles for given feed")
  @ApiResponse(responseCode = "200", description = "Articles fetched successfully")
  public FetchArticlesAPIResponse fetchArticlesForCategory(
      @Parameter(description = "ID of the category to retrieve articles for", required = true)
      @RequestParam("categoryId") Integer categoryId
  ) {
    log.info("[FETCH ARTICLES] Fetching articles for category ID: {}", categoryId);

    FetchArticlesCommand command = FetchArticlesCommand.from(categoryId);
    List<Article> articles = articleManagementUseCase.fetchArticles(command);

    log.info("[FETCH ARTICLES] Fetched {} articles for category ID: {}", articles.size(), categoryId);

    return fetchArticlesMapper.toFetchArticlesAPIResponse(articles);
  }
}
