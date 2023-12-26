package ee.testrest.mobile.ios.saucelabs.screens.checkout;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byName;
import static com.codeborne.selenide.Selenide.element;
import static com.codeborne.selenide.appium.AppiumClickOptions.tap;

public class CheckoutCompleteScreen {
    private final SelenideElement checkoutCompleteConfirmation = element(byName("test-CHECKOUT: COMPLETE!"));
    private final SelenideElement backHomeButton = element(byName("test-BACK HOME"));

    @Step("Verify checkout screen has loaded")
    public CheckoutCompleteScreen load() {
        checkoutCompleteConfirmation.shouldBe(visible);
        return this;
    }

    @Step("Return home to all products screen")
    public <T> T returnHome(Class<T> returnScreen) {
        backHomeButton.click(tap());
        try {
            return returnScreen.getConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Error creating instance of " + returnScreen, e);
        }
    }
}
