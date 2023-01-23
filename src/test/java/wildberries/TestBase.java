package wildberries;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import wildberries.config.ProjectConfiguration;
import wildberries.config.WebConfig;

public class TestBase {

    private static WebConfig config;
    private static ProjectConfiguration configuration;
    @BeforeAll
    static void setUp() {
        Configuration.timeout = 10000;
        config = ConfigFactory.create(WebConfig.class, System.getProperties());
        configuration = new ProjectConfiguration();
        configuration.webConfig(config);
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
    }
}