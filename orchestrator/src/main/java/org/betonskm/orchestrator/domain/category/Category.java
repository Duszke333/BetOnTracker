package org.betonskm.orchestrator.domain.category;

import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Category {

  private Long id;
  private String name;
  private OffsetDateTime createdAt;
  private OffsetDateTime decommissionedAt;
}
