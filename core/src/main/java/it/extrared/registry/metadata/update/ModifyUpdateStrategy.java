package it.extrared.registry.metadata.update;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import it.extrared.registry.metadata.DPPMetadata;
import it.extrared.registry.metadata.DPPMetadataRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

/**
 * An implementation of {@link UpdateStrategy} that modify the existing data on the database.
 */
@ApplicationScoped
public class ModifyUpdateStrategy implements UpdateStrategy {
    @Inject DPPMetadataRepository repository;

    @Override
    public UpdateType supportedType() {
        return UpdateType.MODIFY;
    }

    @Override
    public Uni<DPPMetadata> update(SqlConnection connection, DPPMetadata dppMetadata) {
        return repository.update(connection, dppMetadata);
    }
}
