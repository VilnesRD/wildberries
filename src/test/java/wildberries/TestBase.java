package wildberries;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.remote.DesiredCapabilities;
import wildberries.helpers.Attach;

public class TestBase {

    @BeforeAll
    public static void testBaseUrlConfiguration() {
        Configuration.pageLoadTimeout = 9000;
        Configuration.baseUrl = System.getProperty("baseUrl","https://www.wildberries.ru/");
        Configuration.browserSize = System.getProperty("resolution", "1920x1080");
        Configuration.browser = System.getProperty("browser", "chrome");
        Configuration.remote = System.getProperty("selenoideURL", "https://user1:1234@selenoid.autotests.cloud") + "/wd/hub";
        Configuration.browserVersion = System.getProperty("browserVersion", "99.0");

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("enableVNC", true);
        capabilities.setCapability("enableVideo", true);
        Configuration.browserCapabilities = capabilities;
    }

    @BeforeEach
    void addListener() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
    }

    @AfterEach
    void addAttachments() {
        Attach.screenshotAs("Last screenshot");
        Attach.pageSource();
        Attach.browserConsoleLogs();
        Attach.addVideo();
        Selenide.closeWebDriver();
    }
}