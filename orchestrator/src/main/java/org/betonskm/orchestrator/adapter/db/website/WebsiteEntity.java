package org.betonskm.orchestrator.adapter.db.website;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.betonskm.orchestrator.adapter.db.category.CategoryEntity;
import org.betonskm.orchestrator.adapter.db.categoryWebsite.CategoryWebsite;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@Setter
@Builder
@Table(name = "website")
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class WebsiteEntity {

  @Id
  @Column(name = "id", nullable = false)
  private UUID id;

  @Size(max = 2048)
  @NotNull
  @Column(name = "url", nullable = false, length = 2048)
  private String url;

  @Size(max = 1024)
  @Column(name = "title", length = 1024)
  private String title;

  @Size(max = 512)
  @Column(name = "etag", length = 512)
  private String etag;

  @NotNull
  @CreatedDate
  @Column(name = "created_at", nullable = false)
  private OffsetDateTime createdAt;

  @Column(name = "last_fetched_at")
  private OffsetDateTime lastFetchedAt;

  @NotNull
  @ColumnDefault("1")
  @Column(name = "reference_count", nullable = false)
  private Integer referenceCount;

  @OneToMany(mappedBy = "website", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<CategoryWebsite> categories = new HashSet<>();

}