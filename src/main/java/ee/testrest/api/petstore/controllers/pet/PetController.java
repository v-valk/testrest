package ee.testrest.api.petstore.controllers.pet;

import com.typesafe.config.ConfigFactory;
import ee.testrest.api.config.RestSpecification;
import ee.testrest.api.petstore.controllers.pet.dto.Pet;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;

public class PetController {
    private final String petStoreUri = ConfigFactory.load().getString("petstore.base.uri");
    private final String basePath = "/v2";
    private final String controller = "/pet";
    private final RequestSpecification spec = new RestSpecification(petStoreUri).getSpecification();

    @Step("Create new pet")
    public Response postPet(Pet payload) {
        return given(spec)
                .basePath(basePath)
                .body(payload)
                .post(controller);
    }

    @Step("Find pet by id: {petId}")
    public Response getPet(Integer petId) {
        return given(spec)
                .basePath(basePath)
                .pathParam("petId", petId)
                .get(controller + "/{petId}");
    }

    @Step("Upload an image for the pet with ID {petId}")
    public void uploadImage(int petId, String additionalMetadata, File file) {
        given()
                .baseUri(petStoreUri)
                .basePath(basePath)
                .pathParam("petId", petId)
                .formParam("additionalMetadata", additionalMetadata)
                .formParam("file", file)
                .post(controller + "/{petId}")
                .then()
                .statusCode(SC_OK)
                .extract()
                .response();
    }

    @Step("Delete pet with id: {petId}")
    public void deletePet(Integer petId) {
        given(spec)
                .basePath(basePath)
                .pathParam("petId", petId)
                .when()
                .delete(controller + "/{petId}")
                .then()
                .statusCode(SC_OK)
                .extract()
                .response();
    }
}
