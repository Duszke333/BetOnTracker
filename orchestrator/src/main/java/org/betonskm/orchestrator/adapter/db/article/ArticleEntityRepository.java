package org.betonskm.orchestrator.adapter.db.article;

import aj.org.objectweb.asm.commons.Remapper;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleEntityRepository extends JpaRepository<ArticleEntity, UUID> {

  @Query("FROM ArticleEntity a WHERE "
      + "a.categoryId = :categoryId AND "
      + "a.s3SummaryPath IS NOT NULL AND "
      + "a.createdAt > :since "
      + "ORDER BY a.createdAt DESC "
      + "LIMIT 100")
  List<ArticleEntity> findArticlesForCategory(Integer categoryId, OffsetDateTime since);

  boolean existsByArticleLink(String articleLink);
}
