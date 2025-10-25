package org.betonskm.orchestrator.adapter.api.model.mapper;

import java.util.List;
import org.betonskm.orchestrator.adapter.api.model.response.FetchWebsitesAPIResponse;
import org.betonskm.orchestrator.adapter.api.model.response.WebsiteAPIResponse;
import org.betonskm.orchestrator.configuration.MapperConfiguration;
import org.betonskm.orchestrator.domain.website.Website;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfiguration.class)
public interface WebsiteResponseMapper {

  WebsiteAPIResponse toAPIResponse(Website website);

  FetchWebsitesAPIResponse.WebsiteResponse toWebsiteResponse(Website website);

  List<FetchWebsitesAPIResponse.WebsiteResponse> toWebsiteResponses(List<Website> websites);

  default FetchWebsitesAPIResponse toFetchWebsitesAPIResponse(List<Website> websites) {
    return new FetchWebsitesAPIResponse(toWebsiteResponses(websites));
  }
}
