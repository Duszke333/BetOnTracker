package org.betonskm.orchestrator.configuration.exception.api;

import org.betonskm.orchestrator.configuration.exception.api.ApiExceptionResponseDetailsResolver.ValidationError;
import org.betonskm.orchestrator.configuration.exception.api.ApiExceptionType.ExceptionTypeSeverity;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ApiExceptionResponse {

  @NotNull
  @Schema(
      requiredMode = RequiredMode.REQUIRED,
      example = "3fa85f64-5717-4562-b3fc-2c963f66afa6",
      description = "Error identification code"
  )
  private UUID uuid;

  @NotNull
  @Schema(
      requiredMode = RequiredMode.REQUIRED,
      example = "An unexpected error occurred",
      description = "Error message"
  )
  private String message;

  @NotNull
  @Schema(
      requiredMode = RequiredMode.REQUIRED,
      example = "INTERNAL_SERVER_ERROR",
      description = "Type of the API exception"
  )
  private ApiExceptionType type;

  @NotNull
  @Schema(
      requiredMode = RequiredMode.REQUIRED,
      example = "2024-01-01T12:00:00+00:00",
      description = "Timestamp of when the error occurred"
  )
  private OffsetDateTime timestamp;

  @Schema(
      description = "List of validation errors"
  )
  private List<ValidationError> errors;

  @Builder
  public ApiExceptionResponse(
      @NotNull String message,
      @NotNull ApiExceptionType type,
      Exception exception,
      List<ValidationError> errors) {
    this.message = message;
    this.type = type;
    this.errors = errors;
    this.uuid = UUID.randomUUID();
    this.timestamp = OffsetDateTime.now();

    ExceptionTypeSeverity severity = type.getSeverity();
    switch (severity) {
      case WARNING -> log.warn("{}", this, exception);
      case ERROR -> log.error("{}", this, exception);
    }
  }
}
