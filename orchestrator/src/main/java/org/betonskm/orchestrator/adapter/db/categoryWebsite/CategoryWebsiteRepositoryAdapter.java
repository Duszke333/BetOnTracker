package org.betonskm.orchestrator.adapter.db.categoryWebsite;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.betonskm.orchestrator.application.port.out.CategoryWebsiteRepository;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CategoryWebsiteRepositoryAdapter implements CategoryWebsiteRepository {

  private final CategoryWebsiteEntityRepository categoryWebsiteEntityRepository;

  @Override
  public boolean link(Integer categoryId, UUID websiteId) {
    CategoryWebsiteId id = new CategoryWebsiteId(categoryId, websiteId);
    if (categoryWebsiteEntityRepository.existsById(id)) {
      log.info("Link between category {} and website {} already exists", categoryId, websiteId);
      return false;
    }
    CategoryWebsite link = CategoryWebsite.builder()
        .id(id)
        .build();
    categoryWebsiteEntityRepository.save(link);
    log.info("Linked category {} with website {}", categoryId, websiteId);
    return true;
  }

  @Override
  public List<Integer> fetchCategoryIdsByWebsiteId(UUID websiteId) {
    return categoryWebsiteEntityRepository.getCategoryIdByWebsiteId(websiteId);
  }

  @Override
  public List<UUID> fetchWebsiteIdsByCategoryId(Integer categoryId) {
    return categoryWebsiteEntityRepository.getLinkedWebsites(categoryId);
  }

  @Override
  public void deleteAllLinksForCategory(Integer categoryId) {
    categoryWebsiteEntityRepository.deleteAllByCategoryId(categoryId);
  }
}
