package it.extrared.registry.datastore.mariadb;

import com.fasterxml.jackson.databind.JsonNode;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.vertx.RunOnVertxContext;
import io.quarkus.test.vertx.UniAsserter;
import it.extrared.registry.jsonschema.JsonSchemaDBRepository;
import it.extrared.registry.utils.JsonUtils;
import jakarta.inject.Inject;
import java.io.IOException;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class MariaDBSchemaDBRepositoryTest {

    @Inject JsonSchemaDBRepository schemaDBRepository;

    @Test
    @RunOnVertxContext
    public void testAddNewGetRemoveGet(UniAsserter asserter) throws IOException {
        JsonNode schema = JsonUtils.loadClasspathJsonTemplate("db-schema.json");
        JsonNode current = JsonUtils.loadClasspathJsonTemplate("default-schema.json");
        asserter.assertNull(() -> schemaDBRepository.addSchema(schema));
        asserter.assertEquals(() -> schemaDBRepository.getCurrentJsonSchema(), schema);
        asserter.assertNull(() -> schemaDBRepository.removeLastSchema());
        asserter.assertEquals(() -> schemaDBRepository.getCurrentJsonSchema(), current);
    }
}
