package it.extrared.registry.metadata;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.tuples.Tuple2;
import io.vertx.core.json.JsonObject;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.inject.spi.CDI;
import java.util.List;
import java.util.function.Function;

public interface DPPMetadataRepository {

    Uni<DPPMetadata> findByUpi(SqlConnection conn, String upi);

    Uni<DPPMetadata> findBy(SqlConnection conn, List<Tuple2<String, Object>> filters);

    Uni<DPPMetadata> save(SqlConnection conn, DPPMetadata metadata);

    Uni<DPPMetadata> update(SqlConnection con, DPPMetadata metadata);

    Function<Row, DPPMetadata> ROW_MAPPER =
            r -> {
                ObjectMapper om = CDI.current().select(ObjectMapper.class).get();
                DPPMetadata metadata = new DPPMetadata();
                metadata.setRegistryId(r.getString("registry_id"));
                metadata.setMetadata(
                        om.convertValue(
                                r.get(JsonObject.class, "metadata").getMap(),
                                new TypeReference<JsonNode>() {}));
                metadata.setCreatedAt(r.getLocalDateTime("created_at"));
                metadata.setCreatedAt(r.getLocalDateTime("modified_at"));
                return metadata;
            };
}
