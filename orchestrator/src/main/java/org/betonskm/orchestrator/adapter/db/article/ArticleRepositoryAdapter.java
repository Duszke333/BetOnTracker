package org.betonskm.orchestrator.adapter.db.article;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.betonskm.orchestrator.application.port.out.ArticleRepository;
import org.betonskm.orchestrator.domain.article.Article;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class ArticleRepositoryAdapter implements ArticleRepository {

  private final ArticleEntityRepository articleEntityRepository;
  private final ArticleMapper articleMapper;

  @Override
  public Article save(Article article) {
    return articleMapper.fromEntity(articleEntityRepository.save(articleMapper.toEntity(article)));
  }
}
