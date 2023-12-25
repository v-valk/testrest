package mobile;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.appium.SelenideAppium;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import io.qameta.allure.selenide.LogType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.util.logging.Level;

public class MobileTestSetup {

    @BeforeEach
    protected void openApp() {
        SelenideAppium.launchApp();
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide()
                .screenshots(true)
                .savePageSource(false)
                .enableLogs(LogType.DRIVER, Level.ALL)
                .includeSelenideSteps(true)
        );
    }

    @AfterEach
    protected void closeApp() {
        Selenide.closeWebDriver();
    }
}
