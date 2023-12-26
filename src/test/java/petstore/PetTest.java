package petstore;

import ee.testrest.allure.AllureUtils;
import ee.testrest.api.petstore.controllers.pet.PetController;
import ee.testrest.api.petstore.controllers.pet.dto.Category;
import ee.testrest.api.petstore.controllers.pet.dto.Pet;
import ee.testrest.api.petstore.controllers.pet.dto.Tag;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.*;

import java.io.File;
import java.util.Objects;

import static ee.testrest.api.petstore.controllers.pet.dto.Status.available;
import static ee.testrest.util.FakeData.faker;
import static java.util.Arrays.asList;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@org.junit.jupiter.api.Tag("api")
@DisplayName("Pet Store Tests - Pet Controller")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class PetTest extends PetController {

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Create Pet")
    public void createPetTest() {
        var expectedPet = createNewPetRequest();
        var actualPet = postPet(expectedPet).as(Pet.class);
        assertThat(actualPet)
                .usingRecursiveComparison()
                .isEqualTo(expectedPet);
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Upload Pet Image")
    public void uploadPetImageTest() {
        var newPetRequest = createNewPetRequest();
        var createPetResponse = postPet(newPetRequest).as(Pet.class);
        uploadImageForPet(createPetResponse.id(), "much meta");
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Delete Pet")
    public void deletePetTest() {
        var newPetRequest = createNewPetRequest();
        var petId = postPet(newPetRequest).as(Pet.class).id();

        deletePet(petId);
        var errorMessage = getPet(petId).jsonPath().get("message");
        assertThat(errorMessage)
                .isEqualTo("Pet not found")
                .describedAs("Show a meaningful error because pet has been deleted.");
    }

    private void uploadImageForPet(int petId, String additionalMetadata) {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource("bork.jpeg")).getFile());

        AllureUtils.attachJpegToAllureReport("wow", file.toPath());

        uploadImage(petId, additionalMetadata, file);
    }

    private Pet createNewPetRequest() {
        var id = faker.number().numberBetween(1, 999);
        var category = new Category(faker.number().randomDigit(), faker.dog().breed());
        var name = faker.dog().name();
        var photoUrls = asList(faker.internet().url(), faker.internet().url());
        var tags = asList(new Tag(faker.number().randomDigit(), faker.beer().name()), new Tag(faker.number().randomDigit(), faker.beer().malt()));
        var status = available;

        return new Pet(id, category, name, photoUrls, tags, status);
    }
}
