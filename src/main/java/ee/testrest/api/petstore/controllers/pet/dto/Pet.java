package ee.testrest.api.petstore.controllers.pet.dto;

import java.util.List;

public record Pet(
        int id,
        Category category,
        String name,
        List<String> photoUrls,
        List<Tag> tags,
        Status status
) {
}
