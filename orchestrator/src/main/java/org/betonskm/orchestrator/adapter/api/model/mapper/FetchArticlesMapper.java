package org.betonskm.orchestrator.adapter.api.model.mapper;

import java.util.List;
import org.betonskm.orchestrator.adapter.api.model.response.FetchArticlesAPIResponse;
import org.betonskm.orchestrator.adapter.api.model.response.FetchArticlesAPIResponse.ArticleResponse;
import org.betonskm.orchestrator.configuration.MapperConfiguration;
import org.betonskm.orchestrator.domain.article.Article;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfiguration.class)
public interface FetchArticlesMapper {

  @Mapping(target = "articleUrl", source = "articleLink")
  FetchArticlesAPIResponse.ArticleResponse toArticleResponse(Article article);

  List<ArticleResponse> toArticleResponses(List<Article> articles);

  default FetchArticlesAPIResponse toFetchArticlesAPIResponse(List<Article> articles) {
    return new FetchArticlesAPIResponse(toArticleResponses(articles));
  }
}
