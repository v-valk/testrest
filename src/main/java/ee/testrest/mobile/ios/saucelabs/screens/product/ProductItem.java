package ee.testrest.mobile.ios.saucelabs.screens.product;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.appium.AppiumSelectors;
import io.qameta.allure.Step;

import static com.codeborne.selenide.appium.AppiumSelectors.byName;

public class ProductItem {

    private final SelenideElement container;

    public ProductItem(SelenideElement container) {
        this.container = container;
    }

    private SelenideElement getProductItem() {
        return container.$(AppiumSelectors.byName("test-Item"));
    }

    private SelenideElement getProductName() {
        return container.find(byName("test-Item title"));
    }

    private SelenideElement getPrice() {
        return container.find(byName("test-Price"));
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

