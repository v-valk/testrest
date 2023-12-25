package ee.testrest.api.gorest.controllers.user.dto;

public record UserRequest(String name, String gender, String email, String status) {
}
