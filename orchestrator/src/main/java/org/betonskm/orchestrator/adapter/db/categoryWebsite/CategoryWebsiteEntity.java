package org.betonskm.orchestrator.adapter.db.categoryWebsite;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.betonskm.orchestrator.adapter.db.category.CategoryEntity;
import org.betonskm.orchestrator.adapter.db.website.WebsiteEntity;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Builder
@Table(name = "category_website")
@NoArgsConstructor
@AllArgsConstructor
public class CategoryWebsiteEntity {

  @EmbeddedId
  private CategoryWebsiteEntityId id;

  @MapsId("categoryId")
  @ManyToOne(fetch = FetchType.LAZY)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @ColumnDefault("nextval('category_website_category_id_seq')")
  @JoinColumn(name = "category_id", nullable = false)
  private CategoryEntity category;

  @MapsId("websiteId")
  @ManyToOne(fetch = FetchType.LAZY)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "website_id", nullable = false)
  private WebsiteEntity websiteEntity;

}