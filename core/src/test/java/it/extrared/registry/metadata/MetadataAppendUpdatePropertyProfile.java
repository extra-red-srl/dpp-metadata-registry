package it.extrared.registry.metadata;

import io.quarkus.test.junit.QuarkusTestProfile;
import it.extrared.registry.metadata.update.UpdateType;
import java.util.Map;

public class MetadataAppendUpdatePropertyProfile implements QuarkusTestProfile {
    @Override
    public Map<String, String> getConfigOverrides() {
        return Map.of("registry.update-strategy", UpdateType.APPEND_WITH_NEW_ID.name());
    }
}
