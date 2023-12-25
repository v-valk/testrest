package ee.testrest.api.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.config.LogConfig;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

import static io.restassured.http.ContentType.JSON;

public class RestSpecification {

    private final RequestSpecification specification;

    public RestSpecification(String baseUri) {
        this.specification = createSpecification(baseUri);
    }

    public RestSpecification(String baseUri, String authToken) {
        this.specification = createSpecification(baseUri, authToken);
    }

    public RequestSpecification getSpecification() {
        return specification;
    }

    private RequestSpecification createSpecification(String baseUri) {
        return createSpecification(baseUri, null);
    }

    private RequestSpecification createSpecification(String baseUri, String authToken) {
        RestAssuredConfig config = RestAssuredConfig.config()
                .logConfig(LogConfig.logConfig()
                        .enablePrettyPrinting(true)
                        .blacklistHeader("Jwt")
                        .blacklistHeader("Bearer")
                        .and().enableLoggingOfRequestAndResponseIfValidationFails())
                .objectMapperConfig(getObjectMapperConfig());

        RequestSpecification spec = RestAssured.given()
                .config(config)
                .contentType(JSON)
                .accept(JSON)
                .filter(new AllureRestAssured())
                .filter(new RequestLoggingFilter())
                .filter(new ResponseLoggingFilter())
                .baseUri(baseUri)
                .log().all()
                .urlEncodingEnabled(false);

        if (authToken != null) {
            spec.header("Authorization", "Bearer " + authToken);
        }

        return spec;
    }

    private ObjectMapperConfig getObjectMapperConfig() {
        return new ObjectMapperConfig()
                .jackson2ObjectMapperFactory((cls, charset) -> getObjectMapper());
    }

    private ObjectMapper getObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.configure(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE, false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        SimpleModule module = new SimpleModule();
        objectMapper.registerModule(module);
        return objectMapper;
    }
}
