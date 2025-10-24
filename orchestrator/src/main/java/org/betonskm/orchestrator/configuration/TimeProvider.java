package org.betonskm.orchestrator.configuration;

import java.time.Clock;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Getter
@Service
@RequiredArgsConstructor
public class TimeProvider {

  private final Clock clock;

  public OffsetDateTime now() {
    return OffsetDateTime.now(clock);
  }

  public OffsetDateTime now(ZoneId zoneId) {
    return OffsetDateTime.now(clock.withZone(zoneId));
  }

public OffsetDateTime calculateThreshold(java.time.Duration timeout) {
    return now().minus(timeout);
  }
}
