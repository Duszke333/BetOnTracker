package org.betonskm.orchestrator.adapter.api.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
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
public class FetchAllCategoriesAPIResponse {

  @Schema(
      description = "List of all categories",
      example = "[{\"id\":1,\"name\":\"Category 1\"},{\"id\":2,\"name\":\"Category 2\"}]"
  )
  private List<CategoryAPIResponse> categories;
}
