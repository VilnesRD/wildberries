package wildberries;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import static com.codeborne.selenide.Selenide.open;

public class TestBase {
    @BeforeAll
    static void setUP() {
        Configuration.holdBrowserOpen = true;
        Configuration.browserSize = "1920x1080";
    }

    @BeforeEach
    void config() throws InterruptedException {
        open("https://www.wildberries.ru/");
        Configuration.timeout = 10000;
        Thread.sleep(4000);
    }
}

