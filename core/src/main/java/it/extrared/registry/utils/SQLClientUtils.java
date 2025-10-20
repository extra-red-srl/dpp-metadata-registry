package it.extrared.registry.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.core.json.JsonObject;
import io.vertx.mutiny.sqlclient.RowIterator;
import io.vertx.mutiny.sqlclient.RowSet;
import it.extrared.registry.metadata.DPPMetadata;
import java.util.Iterator;

public class SQLClientUtils {

    public static DPPMetadata firstOrNull(RowSet<DPPMetadata> rs) {
        RowIterator<DPPMetadata> it = rs.iterator();
        if (it.hasNext()) return it.next();
        else return null;
    }

    public static JsonNode getJsonNode(ObjectMapper objectMapper, Iterator<JsonObject> rs) {
        if (rs.hasNext()) return objectMapper.convertValue(rs.next().getMap(), JsonNode.class);
        return null;
    }
}
