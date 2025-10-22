package it.extrared.registry.mocks;

import com.fasterxml.jackson.databind.JsonNode;
import io.quarkus.arc.Unremovable;
import io.smallrye.mutiny.Uni;
import it.extrared.registry.jsonschema.JsonSchemaDBRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
@Unremovable
public class EmptyJsonSchemaDBRepository implements JsonSchemaDBRepository {
    @Override
    public Uni<JsonNode> getCurrentJsonSchema() {
        return Uni.createFrom().nullItem();
    }

    @Override
    public Uni<Void> addSchema(JsonNode schema) {
        return Uni.createFrom().voidItem();
    }

    @Override
    public Uni<Void> removeLastSchema() {
        return Uni.createFrom().voidItem();
    }
}
