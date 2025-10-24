package org.betonskm.orchestrator.adapter.api.model.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.UUID;

public class UUIDValidator implements ConstraintValidator<ValidUUID, String> {

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (value == null) {
      return true; // Use @NotNull if needed
    }
    try {
      UUID.fromString(value);
      return true;
    } catch (Exception ex) {
      return false;
    }
  }
}