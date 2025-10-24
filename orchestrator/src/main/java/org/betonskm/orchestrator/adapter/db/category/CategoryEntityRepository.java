package org.betonskm.orchestrator.adapter.db.category;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoryEntityRepository extends JpaRepository<CategoryEntity, Integer> {

  @Query("FROM CategoryEntity c WHERE c.decommissionedAt IS NULL")
  List<CategoryEntity> findAllActiveCategories();
}
