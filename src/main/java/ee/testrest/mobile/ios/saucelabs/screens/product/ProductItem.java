package ee.testrest.mobile.ios.saucelabs.screens.product;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.appium.AppiumSelectors.byName;

public class ProductItem {

    private final SelenideElement container;

    public ProductItem(SelenideElement container) {
        this.container = container;
    }

    @Step("Add product to cart")
    public <T> T addToCart(Class<T> returnScreen) {
        container.find(byName("test-ADD TO CART")).click();
        try {
            return returnScreen.getConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Error creating instance of " + returnScreen, e);
        }
    }
}

