package ee.testrest.mobile.ios.saucelabs.screens.cart;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.empty;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byName;
import static com.codeborne.selenide.Selenide.element;
import static com.codeborne.selenide.appium.ScreenObject.screen;

public class CartItem {
    public CartItem load() {
        productQuantity.shouldBe(visible);
        productDescription.shouldBe(visible);
        productPrice.shouldBe(visible);
        removeButton.shouldBe(visible);
        return this;
    }

    private final SelenideElement testItem = element(byName("test-Item"));
    private final SelenideElement productQuantity = testItem.find("[name='test-Amount'] [type='XCUIElementTypeStaticText']");
    private final SelenideElement productDescription = testItem.find("[name='test-Description'] [type='XCUIElementTypeStaticText']");
    private final SelenideElement productPrice = testItem.find("[name='test-Price'] [type='XCUIElementTypeStaticText']");
    private final SelenideElement removeButton = element(byName("test-REMOVE"));

    @Step("Verify product details")
    public <T> T verifyProductDetails(Class<T> returnScreen) {
        productQuantity.shouldNotBe(empty);
        productDescription.shouldNotBe(empty);
        productPrice.shouldNotBe(empty);

        try {
            return returnScreen.getConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Error creating instance of " + returnScreen, e);
        }
    }
}
