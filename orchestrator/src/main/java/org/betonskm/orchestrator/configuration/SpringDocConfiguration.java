package org.betonskm.orchestrator.configuration;

import static org.betonskm.orchestrator.configuration.Costants.BASIC_AUTH;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.In;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@OpenAPIDefinition(
    info = @Info(
        title = "Orchestrator API",
        version = "1.0",
        description = "API documentation for the Orchestrator service"
    ),
    servers = {
        @Server(url = "/", description = "Default Server URL")
    }
)
public class SpringDocConfiguration {

  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
        .components(new Components()
            .addSecuritySchemes(BASIC_AUTH, getBasicAuthScheme())
        )
        .addSecurityItem(new SecurityRequirement().addList(BASIC_AUTH));
  }

  private SecurityScheme getBasicAuthScheme() {
    return new SecurityScheme()
        .type(SecurityScheme.Type.HTTP)
        .in(In.HEADER)
        .name("Basic authorization")
        .scheme("basic");
  }
}
