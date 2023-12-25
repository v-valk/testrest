package ee.testrest.api.petstore.controllers.user;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import ee.testrest.allure.Layer;
import ee.testrest.allure.Microservice;
import ee.testrest.api.config.RestSpecification;
import ee.testrest.api.petstore.controllers.user.dto.User;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;

@Layer("rest")
@Owner("Team name who owns this domain might be useful.")
@Microservice("petstore")
public class UserController {
    private final Config config = ConfigFactory.load();
    private final String petStoreUri = config.getString("petstore.base.uri");
    private final String basePath = "/v2";
    private final RequestSpecification spec = new RestSpecification(petStoreUri).getSpecification();

    @Step("Create user")
    public Response createUser(User payload) {
        return given(spec)
                .basePath(basePath)
                .body(payload)
                .when()
                .post("/user")
                .then()
                .statusCode(SC_OK)
                .extract()
                .response();
    }

    @Step("Create multiple users")
    public void createUsers(List<User> payload) {
        given(spec)
                .basePath(basePath)
                .body(payload)
                .when()
                .post("/user/createWithArray")
                .then()
                .statusCode(SC_OK)
                .extract()
                .response();
    }

    @Step("Find user by username: {0.username}")
    public Response getUser(User user) {
        return given(spec)
                .basePath(basePath)
                .pathParam("username", user.username())
                .get("/user/{username}");
    }

    @Step("Delete user: {username}")
    public void deleteUser(String username) {
        given(spec)
                .basePath(basePath)
                .pathParam("username", username)
                .delete("/user/{username}")
                .then()
                .statusCode(SC_OK);
    }

    @Step("Update user details")
    public void updateUser(User originalUser, User updateUserPayload) {
        given(spec)
                .basePath(basePath)
                .body(updateUserPayload)
                .pathParam("username", originalUser.username())
                .put("/user/{username}")
                .then()
                .statusCode(SC_OK);
    }
}
