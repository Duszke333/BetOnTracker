package org.betonskm.orchestrator.domain.website;

import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Website {

  private UUID id;
  private String url;
  private String etag;
  private OffsetDateTime createdAt;
  private OffsetDateTime lastFetchedAt;
  private Integer referenceCount;

  public void incrementReferenceCount() {
    this.referenceCount++;
  }

  public void decrementReferenceCount() {
    if (this.referenceCount > 0) {
      this.referenceCount--;
    }
  }
}
