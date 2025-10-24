package org.betonskm.orchestrator.adapter.db.categoryWebsite;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryWebsiteEntityRepository extends JpaRepository<CategoryWebsite, CategoryWebsiteId> {

  List<CategoryWebsite> findByCategoryId(Long categoryId);

  List<CategoryWebsite> findByWebsiteId(UUID websiteId);

  Optional<CategoryWebsite> findByCategoryIdAndWebsiteId(Long categoryId, UUID websiteId);

  void deleteByCategoryIdAndWebsiteId(Long categoryId, UUID websiteId);
}
