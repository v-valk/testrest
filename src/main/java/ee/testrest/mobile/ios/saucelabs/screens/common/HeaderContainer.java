package ee.testrest.mobile.ios.saucelabs.screens.common;

import com.codeborne.selenide.SelenideElement;
import ee.testrest.mobile.ios.saucelabs.screens.cart.CartScreen;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byName;
import static com.codeborne.selenide.Selenide.element;
import static com.codeborne.selenide.appium.ScreenObject.screen;

public class HeaderContainer {

    public void load() {
        headerContainer.shouldBe(visible);
        menuButton.shouldBe(visible);
        cartButton.shouldBe(visible);
    }

    private final SelenideElement headerContainer = element(byName("headerContainer"));
    private final SelenideElement menuButton = element(byName("test-Menu"));
    private final SelenideElement cartButton = element(byName("test-Cart"));

    @Step("Verify that the cart icon does not show items added")
    public void assertCartIconIsEmpty() {
        cartButton.shouldNotHave(attribute("label"));
    }

    @Step("Open cart screen")
    public CartScreen openCartScreen() {
        cartButton.click();
        return screen(new CartScreen().load());
    }

    @Step("Verify that the cart icon shows {numberOfItems} items added")
    public HeaderContainer assertHeaderCartIconShowsItemsAdded(int numberOfItems) {
        cartButton.shouldHave(attribute("label", String.valueOf(numberOfItems)));
        return this;
    }
}
