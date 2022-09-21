package ru.netology.carddelivery;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999/");
    }

    @Test
    void shouldTestCardDelivery() {
        String date = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $x("//*[@placeholder=\"Город\"]").setValue("Москва");
        $("[placeholder=\"Дата встречи\"]").doubleClick().sendKeys(Keys.DELETE + date);
        $("[name=\"name\"]").setValue("Смит Джон");
        $("[name=\"phone\"]").setValue("+79999999999");
        $("[data-test-id=\"agreement\"]").click();
        $("[class=\"button__text\"]").click();
        $("#root > div > div > div.notification__content").shouldBe(visible, Duration.ofSeconds(15));
        $("#root > div > div > div.notification__title").shouldHave(text("Успешно!"));
        $("#root > div > div > div.notification__content").shouldHave(text("Встреча успешно забронирована на " + date));
    }

    @Test
    void shouldTestCityListOpen() {
        $x("//*[@placeholder=\"Город\"]").setValue("Мо");
        $x("/html/body/div[3]/div/div/div").shouldBe(visible);
    }

    @Test
    void shouldTestCalendarDate() {
        $x("//*[@id=\"root\"]/div/form/fieldset/div[2]/span/span/span/span/span[1]/span/button").click();
        $x("/html/body/div[2]/div/div/div/div/div/table/tbody/tr[6]/td[4]").click();
    }

    @Test
    void shouldTestCalendarDateInNextMonth() {
        $x("//*[@id=\"root\"]/div/form/fieldset/div[2]/span/span/span/span/span[1]/span/button").click();
        $x("/html/body/div[2]/div/div/div/div/div/div/div[4]").click();
        $x("/html/body/div[2]/div/div/div/div/div/table/tbody/tr[4]/td[5]").click();
    }

    @Test
    void shouldTestCalendarDateInAYear() {
        $x("//*[@id=\"root\"]/div/form/fieldset/div[2]/span/span/span/span/span[1]/span/button").click();
        $x("/html/body/div[2]/div/div/div/div/div/table/tbody/tr[1]/th[7]").click();
        $x("/html/body/div[2]/div/div/div/div/div/table/tbody/tr[4]/td[5]").click();
    }
}