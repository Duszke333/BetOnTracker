package org.betonskm.orchestrator.adapter.db.categoryWebsite;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryWebsiteEntityRepository extends JpaRepository<CategoryWebsite, CategoryWebsiteId> {

  @Query(value = "SELECT cw.category_id FROM category_website cw WHERE cw.website_id = :websiteId", nativeQuery = true)
  List<Integer> getCategoryIdByWebsiteId(UUID websiteId);

  @Query(value = "SELECT cw.website_id FROM category_website cw WHERE cw.category_id = :categoryId", nativeQuery = true)
  List<UUID> getLinkedWebsites(Integer categoryId);

  @Modifying
  @Query(value = "DELETE FROM category_website cw WHERE cw.category_id = :categoryId", nativeQuery = true)
  void deleteAllByCategoryId(Integer categoryId);
}
