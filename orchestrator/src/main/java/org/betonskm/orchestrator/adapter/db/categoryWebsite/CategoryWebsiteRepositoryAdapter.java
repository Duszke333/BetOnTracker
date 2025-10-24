package org.betonskm.orchestrator.adapter.db.categoryWebsite;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.betonskm.orchestrator.adapter.db.category.CategoryMapper;
import org.betonskm.orchestrator.adapter.db.website.WebsiteMapper;
import org.betonskm.orchestrator.application.port.out.CategoryWebsiteRepository;
import org.betonskm.orchestrator.domain.category.Category;
import org.betonskm.orchestrator.domain.website.Website;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CategoryWebsiteRepositoryAdapter implements CategoryWebsiteRepository {

  private final CategoryWebsiteEntityRepository categoryWebsiteEntityRepository;
  private final CategoryMapper categoryMapper;
  private final WebsiteMapper websiteMapper;

  @Override
  public boolean link(Category category, Website website) {
    CategoryWebsiteId id = new CategoryWebsiteId(category.getId(), website.getId());
    if (categoryWebsiteEntityRepository.existsById(id)) {
      log.info("Link between category {} and website {} already exists", category.getId(), website.getId());
      return false;
    }
    CategoryWebsite link = CategoryWebsite.builder()
        .id(id)
        .category(categoryMapper.toEntity(category))
        .websiteEntity(websiteMapper.toEntity(website))
        .build();
    categoryWebsiteEntityRepository.save(link);
    log.info("Linked category {} with website {}", category.getId(), website.getId());
    return true;
  }
}
