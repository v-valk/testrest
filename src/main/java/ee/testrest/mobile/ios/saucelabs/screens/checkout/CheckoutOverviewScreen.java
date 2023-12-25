package ee.testrest.mobile.ios.saucelabs.screens.checkout;

import com.codeborne.selenide.SelenideElement;
import ee.testrest.mobile.ios.saucelabs.screens.cart.CartItem;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byName;
import static com.codeborne.selenide.Selenide.element;
import static com.codeborne.selenide.appium.ScreenObject.screen;

public class CheckoutOverviewScreen extends CartItem {

    public CheckoutOverviewScreen load() {
        finishButton.shouldBe(visible);
        cancelButton.shouldBe(visible);
        return this;
    }

    private final SelenideElement finishButton = element(byName("test-FINISH"));
    private final SelenideElement cancelButton = element(byName("test-CANCEL"));

    @Step("Submit order")
    public CheckoutCompleteScreen submitOrder() {
        finishButton.click();
        return screen(new CheckoutCompleteScreen().load());
    }
}
