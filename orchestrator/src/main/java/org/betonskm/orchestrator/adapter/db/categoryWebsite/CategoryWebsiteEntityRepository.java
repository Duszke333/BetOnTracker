package org.betonskm.orchestrator.adapter.db.categoryWebsite;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryWebsiteEntityRepository extends JpaRepository<CategoryWebsite, CategoryWebsiteId> {

}
