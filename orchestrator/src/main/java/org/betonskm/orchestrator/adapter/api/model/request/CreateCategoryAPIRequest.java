package org.betonskm.orchestrator.adapter.api.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.betonskm.orchestrator.adapter.api.model.validation.ValidUUID;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CreateCategoryAPIRequest {

  @Schema(
      requiredMode = RequiredMode.REQUIRED,
      description = "Name of the category",
      example = "Tech News"
  )
  @NotBlank
  private String categoryName;
}
