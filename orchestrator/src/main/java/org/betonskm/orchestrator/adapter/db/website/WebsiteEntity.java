package org.betonskm.orchestrator.adapter.db.website;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
}