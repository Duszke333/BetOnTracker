package org.betonskm.orchestrator.configuration.exception;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import org.betonskm.orchestrator.configuration.exception.api.ApiExceptionResponse;
import org.betonskm.orchestrator.configuration.exception.api.ApiExceptionResponseDetailsResolver;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import java.nio.file.AccessDeniedException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.messaging.handler.annotation.support.MethodArgumentTypeMismatchException;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

  private final ApiExceptionResponseDetailsResolver apiExceptionResponseDetailsResolver;

  @Order(HIGHEST_PRECEDENCE)
  @ResponseStatus(BAD_REQUEST)
  @ExceptionHandler({
      ConstraintViolationException.class,
      MethodArgumentNotValidException.class,
      HttpMessageNotReadableException.class,
      MissingServletRequestParameterException.class
  })
  public ApiExceptionResponse handleBadRequest(Exception ex) {
    return buildApiExceptionResponse(ex);
  }

  @Order(HIGHEST_PRECEDENCE)
  @ResponseStatus(NOT_FOUND)
  @ExceptionHandler({
      EntityNotFoundException.class,
      JpaObjectRetrievalFailureException.class
  })
  public ApiExceptionResponse handleNotFound(Exception ex) {
    return buildApiExceptionResponse(ex);
  }

  @ResponseStatus(UNAUTHORIZED)
  @ExceptionHandler({
      AccessDeniedException.class
  })
  public ApiExceptionResponse handleUnauthorizedException(Exception ex) {
    return buildApiExceptionResponse(ex);
  }

  @ResponseStatus(BAD_REQUEST)
  @ExceptionHandler({
      MethodArgumentTypeMismatchException.class
  })
  public ApiExceptionResponse handleTypeMismatchException(Exception ex) {
    return buildApiExceptionResponse(ex);
  }

  @ResponseStatus(METHOD_NOT_ALLOWED)
  @ExceptionHandler({
      HttpRequestMethodNotSupportedException.class
  })
  public ApiExceptionResponse handleMethodNotAllowedException(Exception ex) {
    return buildApiExceptionResponse(ex);
  }

  @ResponseStatus(INTERNAL_SERVER_ERROR)
  @ExceptionHandler({
      Exception.class,
      OrchestratorException.class
  })
  public ApiExceptionResponse handleGenericException(Exception ex) {
    return buildApiExceptionResponse(ex);
  }

  private ApiExceptionResponse buildApiExceptionResponse(Exception ex) {
    return apiExceptionResponseDetailsResolver.buildApiExceptionResponse(ex);
  }
}
