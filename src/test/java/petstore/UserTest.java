package petstore;

import ee.testrest.api.petstore.controllers.user.UserController;
import ee.testrest.api.petstore.controllers.user.dto.User;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.junit.jupiter.api.*;

import static ee.testrest.util.FakeData.faker;
import static java.util.Arrays.asList;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Tags({@Tag("api"), @Tag("smoke")})
@DisplayName("Pet Store Tests - User Controller")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class UserTest extends UserController {

    @Test
    @DisplayName("Create one user")
    @Description("Create a single user, then verify the user details.")
    public void createOneUserTest() {
        var user = prepareUserPayload();
        createUser(user);

        var retrievedUser = getUser(user).as(User.class);
        assertThat(retrievedUser)
                .usingRecursiveComparison()
                .isEqualTo(user);
    }

    @Test
    @DisplayName("Create multiple users")
    @Description("Create multiple users, then verify each user individually.")
    public void createMultipleUsersTest() {
        var user1 = prepareUserPayload();
        var user2 = prepareUserPayload();
        var userList = asList(user1, user2);

        createUsers(userList);

        var retrievedUser1 = getUser(user1).as(User.class);
        assertThat(retrievedUser1)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .describedAs("Verify user is created as per request, ignore auto assigned id.")
                .isEqualTo(user1);

        var retrievedUser2 = getUser(user2).as(User.class);
        assertThat(retrievedUser2)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .describedAs("Verify user is created as per request, ignore auto assigned id.")
                .isEqualTo(user2);
    }

    @Test
    @DisplayName("Update user")
    @Description("Overwrite original user with new details and verify the old user does not exist.")
    public void updateUserTest() {
        var originalUserDetails = prepareUserPayload().withId(34);
        createUser(originalUserDetails).as(User.class);

        var newDetails = prepareUserPayload().withId(34);
        updateUser(originalUserDetails, newDetails);

        var oldUserNotFoundMessage = getUser(originalUserDetails)
                .body().jsonPath().getString("message");

        assertThat(oldUserNotFoundMessage).isEqualTo("User not found")
                .describedAs("Old user must have been overwritten with the new details.");
    }

    @Test
    @DisplayName("Delete user")
    @Description("Create a user, delete the user, then verify the user is not found.")
    public void deleteUserTest() {
        User user = prepareUserPayload();
        createUser(user);
        deleteUser(user.username());
        verifyUserNotFound(user);
    }

    @Step("Prepare user details")
    private User prepareUserPayload() {
        var id = faker.number().numberBetween(1, 999);
        var username = faker.name().username();
        var firstname = faker.funnyName().name();
        var lastName = faker.name().lastName();
        var email = faker.internet().safeEmailAddress();
        var password = faker.internet().password();
        var phone = faker.phoneNumber().phoneNumber();
        var userStatus = faker.number().randomDigit();
        return new User(
                id,
                username,
                firstname,
                lastName,
                email,
                password,
                phone,
                userStatus
        );
    }
//    @Step("Prepare user details")
//    private User prepareUserPayload() {
//        var id = faker.number().numberBetween(1, 999);
//        var username = faker.name().username();
//        var firstname = faker.funnyName().name();
//        var lastName = faker.name().lastName();
//        var email = faker.internet().safeEmailAddress();
//        var password = faker.internet().password();
//        var phone = faker.phoneNumber().phoneNumber();
//        var userStatus = faker.number().randomDigit();
//        var user = new User()
//                .withId(id)
//                .withUsername(username)
//                .withFirstName(firstname)
//                .withLastName(lastName)
//                .withEmail(email)
//                .withPassword(password)
//                .withPhone(phone)
//                .withUserStatus(userStatus);
//
//        Allure.addAttachment("User", "text/plain", JsonUtil.toJson(user));
//
//        return user;
//    }

    private void verifyUserNotFound(User user) {
        String response = getUser(user).body().jsonPath().get("message");
        assertThat(response).isEqualTo("No such user.");
    }
}
