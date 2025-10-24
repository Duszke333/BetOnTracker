package org.betonskm.orchestrator.adapter.api.model.mapper;

import org.betonskm.orchestrator.adapter.api.model.response.WebsiteAPIResponse;
import org.betonskm.orchestrator.configuration.MapperConfiguration;
import org.betonskm.orchestrator.domain.website.Website;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfiguration.class)
public interface WebsiteResponseMapper {

  WebsiteAPIResponse toAPIResponse(Website website);
}
