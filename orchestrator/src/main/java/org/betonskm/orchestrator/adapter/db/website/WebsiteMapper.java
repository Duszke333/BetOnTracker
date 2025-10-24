package org.betonskm.orchestrator.adapter.db.website;

import org.betonskm.orchestrator.configuration.MapperConfiguration;
import org.betonskm.orchestrator.domain.website.Website;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfiguration.class)
public interface WebsiteMapper {

  Website fromEntity(WebsiteEntity entity);

  WebsiteEntity toEntity(Website website);
}
