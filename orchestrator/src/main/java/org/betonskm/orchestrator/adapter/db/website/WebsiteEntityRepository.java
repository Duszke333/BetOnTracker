package org.betonskm.orchestrator.adapter.db.website;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WebsiteEntityRepository extends JpaRepository<WebsiteEntity, UUID> {

}
