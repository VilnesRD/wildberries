package wildberries;

import com.codeborne.selenide.Condition;


import io.qameta.allure.Severity;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.url;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class WebTest extends TestBase {


    @Test
    void currencyTest() throws InterruptedException {
        $("ul.header__simple-menu li").hover();
        $("form.popup__form label", 2).click();
        Thread.sleep(3000); //понимаю, что bad practice, но без этого не работает
        $("#searchInput").click();
        $("#searchInput").setValue("iphone");
        $("#applySearchBtn").click();
        $(".product-card__brand").shouldBe(Condition.visible);
        $("div.product-card__price span").shouldHave(Condition.text("тг."));

    }


    @Test
    void pictureSearchTest() {
        $("#searchByImageContainer button").click();
        $("label.upload-photo-btn input").uploadFromClasspath("s22.jpeg");
        $(".product-card__brand").$("p.product-card__brand-name span").shouldHave(Condition.text("Samsung"));
    }


    @Test
    void appLinkTest() {
        $("a.google-play").click();
        switchTo().window(1);
        assertEquals("https://play.google.com/store/apps/details?id=com.wildberries.ru", url());
    }


    @ValueSource(strings = {"Брюки", "Верхняя одежда", "Джемперы, водолазки и кардиганы", "Джинсы", "Комбинезоны", "Костюмы",
            "Лонгсливы", "Пиджаки, жилеты и жакеты", "Платья и сарафаны", "Толстовки, свитшоты и худи", "Туники",
            "Футболки и топы", "Халаты", "Шорты", "Юбки", "Белье", "Большие размеры", "Будущие мамы", "Для высоких",
            "Для невысоких", "Одежда для дома", "Офис", "Пляжная мода", "Религиозная", "Свадьба", "Спецодежда и СИЗы",
            "Подарки женщинам"})
    @ParameterizedTest(name = "Проврка наличия вкладок с названием {0} в разделе Женщинам")
    void womanBurgerMenuTest(String name) {
        $(".nav-element__burger").click();
        $("ul.menu-burger__main-list li").click();
        $(".menu-catalog__list-2").shouldHave(Condition.text(name));
    }


    @CsvSource ({
            "iphone, iPhone",
            "s21, Galaxy",
    })
    @ParameterizedTest
    void searchTest(String search, String expected) throws InterruptedException {
        Thread.sleep(5000);
        $("#searchInput").click();
        $("#searchInput").setValue(search);
        $("#applySearchBtn").click();
        $(".product-card__brand").$("p.product-card__brand-name span", 1)
                .shouldHave(Condition.text(expected));

    }
}
