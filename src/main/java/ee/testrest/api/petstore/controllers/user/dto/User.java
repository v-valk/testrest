package ee.testrest.api.petstore.controllers.user.dto;

public record User(
        int id,
        String username,
        String firstName,
        String lastName,
        String email,
        String password,
        String phone,
        int userStatus
) {
    public User withId(Integer id) {
        return new User(id, this.username, this.firstName, this.lastName, this.email, this.password, this.phone, this.userStatus);
    }
}
