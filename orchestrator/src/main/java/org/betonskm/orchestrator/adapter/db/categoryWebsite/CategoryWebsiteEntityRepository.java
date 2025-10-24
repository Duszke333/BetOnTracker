package org.betonskm.orchestrator.adapter.db.categoryWebsite;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryWebsiteEntityRepository extends JpaRepository<CategoryWebsiteEntity, CategoryWebsiteEntityId> {

  List<CategoryWebsiteEntity> findByCategoryId(Long categoryId);

  List<CategoryWebsiteEntity> findByWebsiteId(UUID websiteId);

  Optional<CategoryWebsiteEntity> findByCategoryIdAndWebsiteId(Long categoryId, UUID websiteId);

  void deleteByCategoryIdAndWebsiteId(Long categoryId, UUID websiteId);
}
