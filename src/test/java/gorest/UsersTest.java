package gorest;

import ee.testrest.api.gorest.controllers.user.UsersController;
import ee.testrest.api.gorest.controllers.user.dto.UserRequest;
import ee.testrest.api.gorest.controllers.user.dto.UserResponse;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.*;

import static ee.testrest.util.FakeData.faker;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DisplayName("Go Rest Tests - Users Controller")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@Tags({@Tag("api"), @Tag("gorest")})
public class UsersTest extends UsersController {

    @Test
    @DisplayName("Create new user")
    @Description("Create a new user and verify the user creation.")
    @Severity(SeverityLevel.CRITICAL)
    public void createUserTest() {
        var payload = createNewUserRequest();
        createUser(payload);
    }

    @Test
    @DisplayName("Verify that unauthorized user cannot create a new user")
    @Description("Try to create a new user with invalid authorization and expect an error.")
    @Severity(SeverityLevel.CRITICAL)
    public void restrictUnauthorizedUserCreation() {
        var payload = createNewUserRequest();
        rejectUserCreationWithInvalidAuth(payload);
    }

    @Test
    @DisplayName("Find user")
    @Description("Create a user, find the user, and verify the user details.")
    @Severity(SeverityLevel.CRITICAL)
    public void getUserTest() {
        var payload = createNewUserRequest();
        var userCreated = createUser(payload).as(UserResponse.class);
        var userFound = getUser(userCreated.id()).as(UserResponse.class);

        assertThat(userFound)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(payload);
    }

    @Test
    @DisplayName("Delete user")
    @Description("Create a user, delete the user, and verify the user is not found.")
    @Severity(SeverityLevel.NORMAL)
    public void deleteUserTest() {
        var payload = createNewUserRequest();
        var userId = createUser(payload).as(UserResponse.class).id();
        deleteUser(userId);

        var userNotFoundResponse = returnUserNotFound(userId).jsonPath().get("message");
        assertThat(userNotFoundResponse)
                .isEqualTo("Resource not found")
                .describedAs("Must throw a graceful error when the resource is not found.");
    }

    private UserRequest createNewUserRequest() {
        return new UserRequest(
                faker.funnyName().name(),
                faker.options().option("Male", "Female"),
                faker.internet().safeEmailAddress(),
                faker.options().option("active", "inactive"));
    }
}
