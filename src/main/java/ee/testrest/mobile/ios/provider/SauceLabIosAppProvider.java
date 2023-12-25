package ee.testrest.mobile.ios.provider;

import com.codeborne.selenide.WebDriverProvider;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import io.appium.java_client.remote.AutomationName;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;

import javax.annotation.Nonnull;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class SauceLabIosAppProvider implements WebDriverProvider {

  @Nonnull
  @Override
  public WebDriver createDriver(@Nonnull Capabilities capabilities) {
    return createAppiumDriver();
  }

  private WebDriver createAppiumDriver() {
    XCUITestOptions options = new XCUITestOptions();
    options.setAutomationName(AutomationName.IOS_XCUI_TEST);
    options.setWdaLaunchTimeout(Duration.ofMinutes(10));
    options.setDeviceName("iPhone 14");
    options.setFullReset(false);
    options.setApp(System.getProperty("user.dir") + "/apps/iOS.Simulator.SauceLabs.Mobile.Sample.app.2.7.1.zip");

    try {
      return new IOSDriver(new URL("http://127.0.0.1:4723"), options);
    } catch (MalformedURLException e) {
      throw new IllegalArgumentException(e);
    }
  }
}