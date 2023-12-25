package ee.testrest.mobile.ios.saucelabs.screens.checkout;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.appium.ScreenObject;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byName;
import static com.codeborne.selenide.Selenide.element;
import static com.codeborne.selenide.appium.ScreenObject.screen;

public class CheckoutInformationScreen {

    public CheckoutInformationScreen load() {
        firstName.shouldBe(visible);
        lastName.shouldBe(visible);
        postcode.shouldBe(visible);
        continueButton.shouldBe(visible);
        cancelButton.shouldBe(visible);
        return this;
    }

    private final SelenideElement firstName = element(byName("test-First Name"));
    private final SelenideElement lastName = element(byName("test-Last Name"));
    private final SelenideElement postcode = element(byName("test-Zip/Postal Code"));
    private final SelenideElement continueButton = element(byName("test-CONTINUE"));
    private final SelenideElement cancelButton = element(byName("test-CANCEL"));

    @Step("Submit information and proceed to checkout overview screen")
    public CheckoutOverviewScreen submitCheckoutForm() {
        firstName.setValue("1");
        lastName.setValue("2");
        postcode.setValue("3");
        continueButton.click();
        return ScreenObject.screen(new CheckoutOverviewScreen());
    }
}
