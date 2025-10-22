package it.extrared.registry.metadata;

import com.fasterxml.jackson.databind.JsonNode;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.tuples.Tuple2;
import io.smallrye.mutiny.unchecked.Unchecked;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.SqlConnection;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Base interface for a {@link DPPMetadataEntry}. Define methods for the business logic code to
 * interact with the underlying data source.
 */
public interface DPPMetadataRepository {

    Uni<DPPMetadataEntry> findByUpi(SqlConnection conn, String upi);

    Uni<DPPMetadataEntry> findBy(SqlConnection conn, List<Tuple2<String, Object>> filters);

    Uni<DPPMetadataEntry> save(SqlConnection conn, DPPMetadataEntry metadata);

    Uni<DPPMetadataEntry> update(SqlConnection con, DPPMetadataEntry metadata);

    BiFunction<Row, Function<Row, JsonNode>, DPPMetadataEntry> ROW_MAPPER =
            Unchecked.function(
                    (r, f) -> {
                        DPPMetadataEntry metadata = new DPPMetadataEntry();
                        metadata.setRegistryId(r.getString("registry_id"));
                        metadata.setMetadata(f.apply(r));
                        metadata.setCreatedAt(r.getLocalDateTime("created_at"));
                        metadata.setCreatedAt(r.getLocalDateTime("modified_at"));
                        return metadata;
                    });
}
