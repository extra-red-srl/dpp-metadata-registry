package it.extrared.registry.api.rest;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.databind.JsonNode;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.Header;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class OpenAPIResourceTest {

    @Test
    public void testCustomizedOpenApi() {
        JsonNode res =
                given().when()
                        .header(new Header("Accept", "application/json"))
                        .get("/q/openapi")
                        .then()
                        .statusCode(200)
                        .extract()
                        .body()
                        .as(JsonNode.class);
        res.get("components");
        assertTrue(res.get("components").get("schemas").has("Metadata"));
    }
}
