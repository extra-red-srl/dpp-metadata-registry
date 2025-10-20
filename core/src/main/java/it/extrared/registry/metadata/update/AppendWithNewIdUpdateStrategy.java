package it.extrared.registry.metadata.update;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import it.extrared.registry.metadata.DPPMetadata;
import it.extrared.registry.metadata.DPPMetadataRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

/**
 * An implementation of {@link UpdateStrategy} that perform updates in append only mode, thereby simply adding a new entry
 * with the same UPI of the existing one and a new registryId.
 */
@ApplicationScoped
public class AppendWithNewIdUpdateStrategy implements UpdateStrategy {

    @Inject DPPMetadataRepository repository;

    @Override
    public UpdateType supportedType() {
        return UpdateType.APPEND_WITH_NEW_ID;
    }

    @Override
    public Uni<DPPMetadata> update(SqlConnection connection, DPPMetadata dppMetadata) {
        return repository.save(connection, dppMetadata);
    }
}
