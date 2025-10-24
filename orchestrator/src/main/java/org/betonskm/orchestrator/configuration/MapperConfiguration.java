package org.betonskm.orchestrator.configuration;

import static org.mapstruct.InjectionStrategy.CONSTRUCTOR;

import org.mapstruct.MapperConfig;
import org.mapstruct.ReportingPolicy;

@MapperConfig(unmappedTargetPolicy = ReportingPolicy.ERROR, unmappedSourcePolicy = ReportingPolicy.WARN, componentModel = "spring", injectionStrategy = CONSTRUCTOR)
public class MapperConfiguration {

}
