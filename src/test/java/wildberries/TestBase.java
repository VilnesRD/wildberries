package wildberries;

import com.codeborne.selenide.logevents.SelenideLogger;
import github.config.ConfigReader;
import github.config.ProjectConfiguration;
import github.config.WebConfig;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

import static com.codeborne.selenide.Selenide.closeWebDriver;

public class BaseTest {

    private static final WebConfig webConfig = ConfigReader.read();
    @BeforeAll
    public static void setUp() {
        SelenideLogger.addListener("allure", new AllureSelenide());
        ProjectConfiguration projectConfiguration = new ProjectConfiguration(webConfig);
        projectConfiguration.webConfig();
    }

    @AfterEach
    public void tearDown() {
        closeWebDriver();
    }
}