package ru.netology.carddelivery;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;


public class CardDeliveryTest {

    @BeforeEach
    void setUp() {

//        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--disable-dev-shm-usage");
//        options.addArguments("--no-sandbox");
//        options.addArguments("--headless");
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
        $("#root > div > div > div.notification__content").shouldHave(text("Встреча успешно забронирована на "+ date));
    }
}
