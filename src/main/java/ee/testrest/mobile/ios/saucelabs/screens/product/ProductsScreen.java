package ee.testrest.mobile.ios.saucelabs.screens.product;

import com.codeborne.selenide.ElementsCollection;
import ee.testrest.mobile.ios.saucelabs.screens.common.HeaderContainer;
import io.qameta.allure.Step;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.elements;
import static com.codeborne.selenide.appium.AppiumSelectors.byName;
import static com.codeborne.selenide.appium.ScreenObject.screen;


public class ProductsScreen {

    private final ElementsCollection allProducts = elements(byName("test-Item"));
    private final HeaderContainer headerContainer = new HeaderContainer();

    public ProductsScreen load() {
        headerContainer.load();
        allProducts.shouldHave(sizeGreaterThan(0));
        return this;
    }

    @Step("Find product: {productName}")
    public ProductItem findProduct(String productName) {
        return new ProductItem(allProducts.findBy(text(productName)));
    }
}
