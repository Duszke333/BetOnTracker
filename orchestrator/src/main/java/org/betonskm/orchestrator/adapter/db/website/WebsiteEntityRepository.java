package org.betonskm.orchestrator.adapter.db.website;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WebsiteEntityRepository extends JpaRepository<WebsiteEntity, UUID> {

}
