package ee.testrest.api.gorest.controllers.user;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import ee.testrest.api.config.RestSpecification;
import ee.testrest.api.gorest.controllers.user.dto.UserRequest;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.*;

public class UsersController {

    private final Config config = ConfigFactory.load();
    private final String gorestUri = config.getString("gorest.base.uri");
    private final String token = config.getString("gorest.auth.token");
    private final String basePath = "/v2";
    private final String controller = "/users";
    private final RequestSpecification spec = new RestSpecification(gorestUri, token).getSpecification();

    @Step("Create user")
    public Response createUser(UserRequest request) {
        return given(spec)
                .basePath(basePath)
                .body(request)
                .post(controller)
                .then()
                .statusCode(SC_CREATED)
                .extract()
                .response();
    }

    @Step("Deny new user creation for unauthorized users")
    public void rejectUserCreationWithInvalidAuth(UserRequest request) {
        var fakeSpec = new RestSpecification(gorestUri, "incorrect token to trigger failure");
        given(fakeSpec.getSpecification())
                .basePath(basePath)
                .body(request)
                .post(controller)
                .then()
                .statusCode(SC_UNAUTHORIZED)
                .extract()
                .response();
    }

    @Step("Find user")
    public Response getUser(Integer userId) {
        return given(spec)
                .basePath(basePath)
                .pathParam("userId", userId)
                .get(controller + "/{userId}")
                .then()
                .statusCode(SC_OK)
                .extract()
                .response();
    }

    @Step("Verify user not found")
    public Response returnUserNotFound(Integer userId) {
        return given(spec)
                .basePath(basePath)
                .pathParam("userId", userId)
                .get(controller + "/{userId}")
                .then()
                .statusCode(SC_NOT_FOUND)
                .extract()
                .response();
    }

    @Step("Delete user: {userId}")
    public void deleteUser(Integer userId) {
        given(spec)
                .basePath(basePath)
                .pathParam("userId", userId)
                .delete(controller + "/{userId}")
                .then()
                .statusCode(SC_NO_CONTENT);
    }
}
