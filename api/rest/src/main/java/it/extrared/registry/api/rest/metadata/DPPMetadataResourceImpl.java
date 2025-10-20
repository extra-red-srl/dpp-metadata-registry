package it.extrared.registry.api.rest.metadata;

import com.fasterxml.jackson.databind.JsonNode;
import io.smallrye.mutiny.Uni;
import it.extrared.registry.api.rest.RestUtils;
import it.extrared.registry.metadata.DPPMetadata;
import it.extrared.registry.metadata.DPPMetadataService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.RestResponse;

import java.util.List;

@ApplicationScoped
public class DPPMetadataResourceImpl implements DPPMetadataResource {
    @Inject
    DPPMetadataService service;
    @Override
    public Uni<RestResponse<DPPMetadata>> addDPPMetadata(List<String> autocompleteBy, JsonNode jsonNode) {
        return service.saveOrUpdate(jsonNode, autocompleteBy).map(m-> RestUtils.respWithBodyAndStatus(Response.Status.CREATED,m));
    }
}
