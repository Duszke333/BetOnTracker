package org.betonskm.orchestrator.configuration.annotations;

import static org.betonskm.orchestrator.configuration.Constants.BASIC_AUTH;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@SecurityRequirement(name = BASIC_AUTH)
public @interface DefaultApiSecurity {

}
