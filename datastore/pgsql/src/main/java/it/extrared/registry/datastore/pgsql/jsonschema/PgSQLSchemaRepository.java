/*
 * Copyright 2025-2026 ExtraRed
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package it.extrared.registry.datastore.pgsql.jsonschema;

import static it.extrared.registry.utils.SQLClientUtils.getJsonNode;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.unchecked.Unchecked;
import io.vertx.core.json.JsonObject;
import io.vertx.mutiny.sqlclient.*;
import it.extrared.registry.jsonschema.JsonSchemaDBRepository;
import it.extrared.registry.utils.JsonUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.time.LocalDateTime;

/** PostgreSQL implementation of the {@link JsonSchemaDBRepository} */
@ApplicationScoped
public class PgSQLSchemaRepository implements JsonSchemaDBRepository {

    @Inject Pool pool;

    @Inject ObjectMapper objectMapper;

    private static final String SELECT_MAX = "SELECT MAX(created_at) FROM json_schemas";

    private static final String SELECT_CURRENT =
                    """
                            SELECT jschema.data_schema
                            FROM json_schemas jschema
                            WHERE created_at = (%s);
            """
                    .formatted(SELECT_MAX);

    private static final String REMOVE_CURRENT =
                    """
                            DELETE
                            FROM json_schemas
                            WHERE created_at = (%s);
            """
                    .formatted(SELECT_MAX);

    private static final String INSERT_SCHEMA =
            """
            INSERT INTO json_schemas (data_schema,created_at)
            VALUES($1,$2);
            """;

    @Override
    public Uni<JsonNode> getCurrentJsonSchema() {
        Uni<RowSet<JsonObject>> result =
                pool.query(SELECT_CURRENT).mapping(r -> r.getJsonObject(0)).execute();
        return result.map(Unchecked.function(rs -> getJsonNode(rs.iterator())));
    }

    @Override
    public Uni<Void> addSchema(JsonNode schema) {
        return pool.withTransaction(
                        c ->
                                pool.preparedQuery(INSERT_SCHEMA)
                                        .execute(
                                                Tuple.of(
                                                        JsonUtils.toVertxJson(schema),
                                                        LocalDateTime.now())))
                .replaceWithVoid();
    }

    @Override
    public Uni<Void> removeLastSchema() {
        return pool.withTransaction(c -> pool.query(REMOVE_CURRENT).execute()).replaceWithVoid();
    }
}
