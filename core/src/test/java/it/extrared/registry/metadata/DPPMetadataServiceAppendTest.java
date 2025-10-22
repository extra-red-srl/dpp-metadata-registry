package it.extrared.registry.metadata;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;
import io.quarkus.test.vertx.RunOnVertxContext;
import io.quarkus.test.vertx.UniAsserter;
import it.extrared.registry.TestSupport;
import jakarta.inject.Inject;
import java.util.List;
import org.junit.jupiter.api.Test;

@QuarkusTest
@TestProfile(MetadataAppendUpdatePropertyProfile.class)
public class DPPMetadataServiceAppendTest extends TestSupport {

    private static final String METADATA_UPDATE =
            """
    {
        "reoId":"6789",
        "upi":"6789",
        "commodityCode":"122267310"
    }
    """;

    @Inject ObjectMapper om;
    @Inject DPPMetadataService metadataService;

    @Test
    @RunOnVertxContext
    public void testUpdateWithAutocomplete(UniAsserter asserter) throws JsonProcessingException {
        JsonNode upd = om.readTree(METADATA_UPDATE);
        asserter.assertThat(
                () -> metadataService.saveOrUpdate(upd, List.of("reoId")),
                m -> {
                    assertEquals(
                            upd.get("commodityCode").asText(),
                            m.getMetadata().get("commodityCode").asText());
                    assertEquals(2, m.getMetadata().get("dataCarrierTypes").size());
                    assertNotNull(m.getRegistryId());
                });
    }
}
