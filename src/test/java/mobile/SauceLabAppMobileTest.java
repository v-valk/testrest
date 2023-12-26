package mobile;

import ee.testrest.mobile.ios.saucelabs.screens.checkout.CheckoutOverviewScreen;
import ee.testrest.mobile.ios.saucelabs.screens.common.HeaderContainer;
import ee.testrest.mobile.ios.saucelabs.screens.login.LoginScreen;
import ee.testrest.mobile.ios.saucelabs.screens.product.ProductsScreen;
import io.qameta.allure.Description;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.ResourceLock;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static com.codeborne.selenide.appium.ScreenObject.screen;

@Tag("ios")
@DisplayName("Mobile tests - Sauce Lab iOS app")
@DisplayNameGeneration(ReplaceUnderscores.class)
class SauceLabAppMobileTest extends MobileTestSetup {

    @ParameterizedTest
    @CsvSource({
            "standard_user, secret_sauce",
            "unknown_user, wrong_password"
    })
    @Description("User can log in with valid credentials")
    @DisplayName("User can log in successfully")
    @ResourceLock("disable parallel execution for mobile")
    void userCanLogIn(String username, String password) {
        new LoginScreen().loginAs(username, password);
    }

    @Test
    @DisplayName("User can add product to cart")
    @ResourceLock("disable parallel execution for mobile")
    void addProductToCartTest() {
        var productName = "Sauce Labs Backpack";
        screen(LoginScreen.class).loginAsStandardUser();

        screen(ProductsScreen.class)
                .findProduct(productName)
                .addToCart(HeaderContainer.class)
                .assertHeaderCartIconShowsItemsAdded(1);
    }

    @Test
    @DisplayName("User can complete checkout flow")
    @ResourceLock("disable parallel execution for mobile")
    void addProductToCart() {
        var productName = "Sauce Labs Backpack";
        screen(LoginScreen.class).loginAsStandardUser();

        screen(ProductsScreen.class)
                .findProduct(productName)
                .addToCart(HeaderContainer.class)
                .openCartScreen()
                .proceedToCheckout()
                .submitCheckoutForm()
                .verifyProductDetails(CheckoutOverviewScreen.class)
                .submitOrder()
                .returnHome(HeaderContainer.class)
                .assertCartIconIsEmpty();
    }
}
