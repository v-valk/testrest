package ee.testrest.mobile.ios.saucelabs.screens.login;

import com.codeborne.selenide.appium.SelenideAppiumElement;
import ee.testrest.mobile.ios.saucelabs.screens.product.ProductsScreen;
import io.qameta.allure.Step;

import static com.codeborne.selenide.appium.AppiumSelectors.byName;
import static com.codeborne.selenide.appium.ScreenObject.screen;
import static com.codeborne.selenide.appium.SelenideAppium.$;

public class LoginScreen {
    private final SelenideAppiumElement username = $(byName("test-Username"));
    private final SelenideAppiumElement password = $(byName("test-Password"));
    private final SelenideAppiumElement loginButton = $(byName("test-LOGIN"));

    @Step("Log in with username: {username}, password: {password}")
    public ProductsScreen loginAs(String name, String pass) {
        username.setValue(name);
        password.setValue(pass);
        loginButton.tap();
        return screen(new ProductsScreen().load());
    }

    @Step("Log in as standard user")
    public ProductsScreen loginAsStandardUser() {
        username.setValue("standard_user");
        password.setValue("secret_sauce");
        loginButton.tap();
        return screen(new ProductsScreen().load());
    }
}
