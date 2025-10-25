package org.betonskm.orchestrator.adapter.db.website;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WebsiteEntityRepository extends JpaRepository<WebsiteEntity, UUID> {

  Optional<WebsiteEntity> findByUrl(String url);

  @Query("FROM WebsiteEntity w WHERE w.referenceCount > 0")
  List<WebsiteEntity> findActiveWebsites();

  @Query(
      value = "SELECT w.* " +
          "FROM website w " +
          "JOIN category_website wc ON w.id = wc.website_id " +
          "WHERE wc.category_id = :categoryId",
      nativeQuery = true
  )
  List<WebsiteEntity> getWebsitesByCategoryId(@Param("categoryId") Integer categoryId);
}
