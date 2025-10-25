package org.betonskm.orchestrator.adapter.db.article;

import org.betonskm.orchestrator.configuration.MapperConfiguration;
import org.betonskm.orchestrator.domain.article.Article;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfiguration.class)
public interface ArticleMapper {

  Article fromEntity(ArticleEntity entity);

  ArticleEntity toEntity(Article article);
}
