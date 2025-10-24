package org.betonskm.orchestrator.adapter.db.website;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.betonskm.orchestrator.application.port.out.WebsiteRepository;
import org.betonskm.orchestrator.domain.website.Website;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebsiteRepositoryAdapter implements WebsiteRepository {

  private final WebsiteEntityRepository repository;
  private final WebsiteMapper mapper;

  @Override
  public Website save(Website website) {
    return mapper.fromEntity(repository.save(mapper.toEntity(website)));
  }

  @Override
  public Optional<Website> fetchByUrl(String url) {
    return repository.findByUrl(url).map(mapper::fromEntity);
  }
}
