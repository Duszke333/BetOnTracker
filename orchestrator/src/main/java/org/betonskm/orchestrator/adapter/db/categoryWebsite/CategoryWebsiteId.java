package org.betonskm.orchestrator.adapter.db.categoryWebsite;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CategoryWebsiteId implements Serializable {

  @Serial
  private static final long serialVersionUID = 2622231438285495094L;
  @NotNull
  @Column(name = "category_id", nullable = false)
  private Integer categoryId;

  @NotNull
  @Column(name = "website_id", nullable = false)
  private UUID websiteId;
}