package org.betonskm.orchestrator.adapter.api.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CategoryAPIResponse {

  @Schema(
      requiredMode = RequiredMode.REQUIRED,
      description = "Unique identifier of the created category",
      example = "1"
  )
  private Integer categoryId;

  @Schema(
      requiredMode = RequiredMode.REQUIRED,
      description = "Name of the created category",
      example = "Tech News"
  )
  private String categoryName;
}
