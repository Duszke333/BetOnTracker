package org.betonskm.orchestrator.adapter.db.category;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryEntityRepository extends JpaRepository<CategoryEntity, Integer> {

  @Query("FROM CategoryEntity c WHERE c.decommissionedAt IS NULL")
  List<CategoryEntity> findAllActiveCategories();

  @Query("SELECT c.name FROM CategoryEntity c WHERE c.id = :id")
  Optional<String> findNameById(Integer id);
}
