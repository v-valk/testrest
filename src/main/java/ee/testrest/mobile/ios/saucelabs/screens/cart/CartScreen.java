package ee.testrest.mobile.ios.saucelabs.screens.cart;

import com.codeborne.selenide.SelenideElement;
import ee.testrest.mobile.ios.saucelabs.screens.checkout.CheckoutInformationScreen;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byName;
import static com.codeborne.selenide.Selenide.element;
import static com.codeborne.selenide.appium.ScreenObject.screen;

public class CartScreen extends CartItem {

    public CartScreen load() {
        checkoutButton.shouldBe(visible);
        continueShoppingButton.shouldBe(visible);
        return this;
    }

    //    private final SelenideElement testItem = element(byName("test-Item"));
//    private final SelenideElement productQuantity = testItem.find("[name='test-Amount'] [type='XCUIElementTypeStaticText']");
//    private final SelenideElement productDescription = testItem.find("[name='test-Description'] [type='XCUIElementTypeStaticText']");
//    private final SelenideElement productPrice = testItem.find("[name='test-Price'] [type='XCUIElementTypeStaticText']");
    private final SelenideElement checkoutButton = element(byName("test-CHECKOUT"));
    private final SelenideElement continueShoppingButton = element(byName("test-CONTINUE SHOPPING"));

//    @Step("Verify product details")
//    public CartScreen verifyProductDetails() {
//        productQuantity.shouldNotBe(empty);
//        productDescription.shouldNotBe(empty);
//        productPrice.shouldNotBe(empty);
//        return this;
//    }

    @Step("Proceed to checkout screen")
    public CheckoutInformationScreen proceedToCheckout() {
        checkoutButton.click();
        return screen(new CheckoutInformationScreen().load());
    }

}
