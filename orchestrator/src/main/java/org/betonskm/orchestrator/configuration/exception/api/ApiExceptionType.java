package org.betonskm.orchestrator.configuration.exception.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApiExceptionType {
  UNAUTHORIZED(ExceptionTypeSeverity.WARNING),
  FORBIDDEN(ExceptionTypeSeverity.WARNING),
  VALIDATION(ExceptionTypeSeverity.WARNING),
  NOT_FOUND(ExceptionTypeSeverity.WARNING),
  EXTERNAL_SERVER_ERROR(ExceptionTypeSeverity.ERROR),
  INTERNAL_SERVER_ERROR(ExceptionTypeSeverity.ERROR);

  private final ExceptionTypeSeverity severity;

  public enum ExceptionTypeSeverity {
    WARNING,
    ERROR
  }
}
