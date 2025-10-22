package it.extrared.registry.jsonschema;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.vertx.RunOnVertxContext;
import io.quarkus.test.vertx.UniAsserter;
import io.smallrye.mutiny.Uni;
import it.extrared.registry.TestSupport;
import it.extrared.registry.utils.JsonUtils;
import jakarta.inject.Inject;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@QuarkusTest
public class JsonSchemaLoaderTest extends TestSupport {

    @Inject SchemaCache schemaCache;
    @Inject ObjectMapper objectMapper;

    @InjectMock JsonSchemaDBRepository repository;

    @BeforeEach
    public void before() {
        schemaCache.invalidate();
    }

    @Test
    @RunOnVertxContext
    public void testDefaultLoading(UniAsserter asserter) throws IOException {
        Mockito.when(repository.getCurrentJsonSchema()).thenReturn(Uni.createFrom().nullItem());
        JsonNode test =
                objectMapper.readTree(
                        getClass().getResourceAsStream("/json-schema/default-schema.json"));
        asserter.assertEquals(() -> schemaCache.get().map(Schema::getSchema), test);
    }

    @Test
    @RunOnVertxContext
    public void testDbLoading(UniAsserter asserter) throws IOException {
        Mockito.when(repository.getCurrentJsonSchema())
                .thenReturn(
                        Uni.createFrom()
                                .item(JsonUtils.loadClasspathJsonTemplate("db-schema.json")));
        JsonNode test =
                objectMapper.readTree(
                        getClass().getResourceAsStream("/json-schema/db-schema.json"));
        asserter.assertEquals(() -> schemaCache.get().map(Schema::getSchema), test);
    }
}
