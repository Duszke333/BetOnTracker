package org.betonskm.orchestrator.configuration.annotations;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ApiResponses({
    @ApiResponse(responseCode = "400", description = "Bad request: validation or type mismatch errors"),
    @ApiResponse(responseCode = "401", description = "Unauthorized: access denied"),
    @ApiResponse(responseCode = "404", description = "Not found: entity not found"),
    @ApiResponse(responseCode = "405", description = "Method not allowed"),
    @ApiResponse(responseCode = "500", description = "Internal server error")
})
public @interface DefaultApiExceptions {
}
