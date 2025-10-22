package it.extrared.registry.jsonschema;

import io.quarkus.test.junit.QuarkusTestProfile;
import java.net.URL;
import java.util.Map;
import java.util.Objects;

public class JsonSchemaSystemPropertyProfile implements QuarkusTestProfile {
    @Override
    public Map<String, String> getConfigOverrides() {
        URL url = JsonSchemaLoaderTest.class.getResource("/json-schema/fs-schema.json");
        Objects.requireNonNull(url, "Schema resource not found");
        return Map.of("registry.json-schema-location", url.toExternalForm());
    }
}
