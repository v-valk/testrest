package ee.testrest.api.gorest.controllers.user.dto;

public record UserResponse(Integer id, String name, String gender, String email, String status) {
}
