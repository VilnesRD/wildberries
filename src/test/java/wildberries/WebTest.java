package wildberries;

import com.codeborne.selenide.Condition;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.testng.xml.dom.Tag;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.url;
import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class WebTest extends TestBase {

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
            $("div.product-card__brand").$("p.product-card__brand-name span").
                    shouldBe(visible,Duration.ofSeconds(20)).shouldHave(Condition.text("Samsung"));
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


    @CsvFileSource(resources={"/woman.csv"}, delimiter = ';')
    @Tag(name="NORMAL")
    @ParameterizedTest(name = "Проверка наличия вкладок с названием {0} в разделе Женщинам")
    @Owner("Dmitry Rodichev")
    @Severity(value = SeverityLevel.NORMAL)
    @DisplayName("Проверка наличия вкладок в разделе Женщинам")
    void womanBurgerMenuTest(String name) {
        step("Открываем главную страницу", () -> {
            Selenide.open("https://www.wildberries.ru/");
        });
        step("Переходим на страницу Женщинам", () -> {
            $("div.header__nav-element button").click();
            $("ul.menu-burger__main-list li").shouldBe(visible, Duration.ofSeconds(30)).click();
        });
        step("Проверяем, что открылась страница Женщинам со списком вкладок {0}", () -> {
            $(".menu-catalog__list-2").shouldHave(Condition.text(name));
        });
    }


    @CsvSource ({
            "iphone, iPhone",
            "Samsung, Samsung",
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
            $("#applySearchBtn").click();
        });
        step("Проверяем, что открылась страница с , соответствующего производителя", () -> {
            $(".product-card__brand").$("p.product-card__brand-name span", 1).shouldBe(visible, Duration.ofSeconds(7))
                    .shouldHave(Condition.text(expected));
        });
    }
}
