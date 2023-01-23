package wildberries;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.testng.xml.dom.Tag;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byCssSelector;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.switchTo;
import static com.codeborne.selenide.WebDriverRunner.url;
import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.assertEquals;

class WebTest extends TestBase {

    @Test
    @Tag(name="CRITICAL")
    @Owner("Dmitry Rodichev")
    @Severity(value = io.qameta.allure.SeverityLevel.CRITICAL)
    @DisplayName("Проверка смены валюты в карточке товара")
    void currencyTest() {
        step("Открываем главную страницу", () -> {
            Selenide.open("https://www.wildberries.ru/");
        });
        step("Выбираем валюту", () -> {
            $("ul.header__simple-menu li").hover();
            $("form.popup__form label", 2).click();
        });
        step("В поисковой строке вводим название товара", () -> {
            $("#searchInput").shouldBe(visible, Duration.ofSeconds(15)).click();
            $("#searchInput").shouldBe(visible, Duration.ofSeconds(15)).setValue("iphone");
            $("#applySearchBtn").click();
        });
        step("Проверяем, что цена товара отображается в выбранной валюте", () -> {
            $(byCssSelector("div.product-card__price ins")).shouldHave(Condition.text("тг."));
        });
    }

    @Test
    @Tag(name="CRITICAL")
    @Owner("Dmitry Rodichev")
    @Severity(value = io.qameta.allure.SeverityLevel.CRITICAL)
    @DisplayName("Проверка поиска по картинке")
    void pictureSearchTest() {
        step("Открываем главную страницу", () -> {
            Selenide.open("https://www.wildberries.ru/");
        });
        step("В поисковой строке выбираем поиск по изображению", () -> {
            $("#searchByImageContainer button").shouldBe(visible,Duration.ofSeconds(15)).click();
        });
        step("Загружаем картинку", () -> {
            $("label.upload-photo-btn input").uploadFromClasspath("s22.jpeg");
        });
        step("Проверяем, что выдача поиска содержит товары", () -> {
            $("div.product-card__brand").$("p.product-card__brand-name span",1).
                    shouldBe(visible,Duration.ofSeconds(20)).shouldHave(Condition.text("Galaxy"));
        });
    }


    @Test
    @Tag(name="CRITICAL")
    @Owner("Dmitry Rodichev")
    @Severity(value = io.qameta.allure.SeverityLevel.CRITICAL)
    @DisplayName("Проверка открытия страницы приложения wb в google play")
    void appLinkTest() {
        step("Открываем главную страницу", () -> {
            Selenide.open("https://www.wildberries.ru/");
        });
        step("Переходим на страницу приложения wb в google play по ссылке на сайте wb", () -> {
            $("a.google-play").click();
            switchTo().window(1);
        });
        step("Проверяем, что открылась страница приложения wb в google play", () -> {
            assertEquals("https://play.google.com/store/apps/details?id=com.wildberries.ru", url());
        });
    }

    @CsvSource ({
            "iphone, iPhone",
            "s21 смартфон, Galaxy",
    })
    @ParameterizedTest
    @Tag(name="CRITICAL")
    @Owner("Dmitry Rodichev")
    @Severity(value = SeverityLevel.NORMAL)
    @DisplayName("Проверка работы поиска при введении названия товара")
    void searchTest(String search, String expected) {
        step("Открываем главную страницу", () -> {
            Selenide.open("https://www.wildberries.ru/");
        });
        step("Вводим в строку поиска название товара", () -> {
            $("#searchInput").shouldBe(visible, Duration.ofSeconds(15)).click();
            $("#searchInput").shouldBe(visible, Duration.ofSeconds(15)).setValue(search);
            $("#applySearchBtn").shouldBe(visible).click();
        });
        step("Проверяем, что открылась страница с , соответствующего производителя", () -> {
            $(".product-card__brand").$("p.product-card__brand-name span", 1).shouldBe(visible, Duration.ofSeconds(20))
                    .shouldHave(Condition.text(expected));
        });
    }
}
