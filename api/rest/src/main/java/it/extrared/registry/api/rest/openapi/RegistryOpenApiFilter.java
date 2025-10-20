package it.extrared.registry.api.rest.openapi;

import io.quarkus.smallrye.openapi.OpenApiFilter;
import org.eclipse.microprofile.openapi.OASFilter;
import org.eclipse.microprofile.openapi.models.OpenAPI;
import org.eclipse.microprofile.openapi.models.media.Schema;

import java.util.Map;

@OpenApiFilter(OpenApiFilter.RunStage.RUN)
public class RegistryOpenApiFilter implements OASFilter {


    @Override
    public void filterOpenAPI(OpenAPI openAPI) {
        OASFilter.super.filterOpenAPI(openAPI);
        Map<String,Schema> schemas=openAPI.getComponents().getSchemas();

    }
}
