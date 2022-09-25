package ru.netology.carddelivery;

import com.codeborne.selenide.conditions.Interactable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.nio.channels.InterruptedByTimeoutException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {

    public String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        open("http://localhost:9999/");
    }

    @Test
    void shouldTestCardDelivery() {
        String meetingDate = generateDate(3);
        $("[placeholder='Город']").setValue("Москва");
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(Keys.DELETE + meetingDate);
        $("[name='name']").setValue("Смит Джон-Джуниор");
        $("[name='phone']").setValue("+79999999999");
        $("[data-test-id='agreement']").click();
        $(".button__text").click();
        $(".notification__title").shouldBe(visible, Duration.ofSeconds(15));
        $(".notification__title").shouldHave(exactText("Успешно!"));
        $(".notification__content").shouldHave(exactText("Встреча успешно забронирована на " + meetingDate));
    }

    @Test
    void shouldTestDropdowns() {
        $("[placeholder='Город']").setValue("Мо");
        $(".input__popup>div>div>div>div>div:nth-child(3)").click();
        $("[class='icon icon_size_m icon_name_calendar icon_theme_alfa-on-white']").click();
        $("[class='icon icon_size_m icon_name_calendar icon_theme_alfa-on-white']").shouldBe(interactable, Duration.ofSeconds(3));
//        $("[data-day='1664485200000']").shouldBe(interactable);
//        WebElement findDate
//        wait();
//        List<WebElement> date = wait.until(
//        ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("[class='icon icon_size_m icon_name_calendar icon_theme_alfa-on-white' + '[data-day='1664485200000']")));

        $("[data-day='1664485200000']").click();
//        $("[placeholder='Дата встречи']").shouldBe(visible, Duration.ofSeconds(1));
        String meetingDate = $("[placeholder='Дата встречи']").getValue();
        $("[name='name']").setValue("Смит Джон");
        $("[name='phone']").setValue("+79999999999");
        $("[data-test-id='agreement']").click();
        $(".button__text").click();
        $(".notification__title").shouldBe(visible, Duration.ofSeconds(15));
        $(".notification__title").shouldHave(exactText("Успешно!"));
        $(".notification__content").shouldHave(exactText("Встреча успешно забронирована на " + meetingDate));
    }

    @Test
    void shouldTestCalendarDateNextMonth() {
        $("[placeholder='Город']").setValue("Юж");
        $("[class='menu-item__control']").click();
        $("[class='icon icon_size_m icon_name_calendar icon_theme_alfa-on-white']").click();
        $("[class='calendar__arrow calendar__arrow_direction_right']").click();
        $("[data-day='1665435600000']").click();
        String meetingDate = $("[placeholder='Дата встречи']").getValue();
        $("[name='name']").setValue("Смит Джон");
        $("[name='phone']").setValue("+79999999999");
        $("[data-test-id='agreement']").click();
        $(".button__text").click();
        $(".notification__title").shouldBe(visible, Duration.ofSeconds(15));
        $(".notification__title").shouldHave(exactText("Успешно!"));
        $(".notification__content").shouldHave(exactText("Встреча успешно забронирована на " + meetingDate));
    }

    @Test
    void shouldTestCalendarDateNextYear() {
        $("[placeholder='Город']").setValue("Мо");
        $(".input__popup>div>div>div>div>div:nth-child(3)").click();
        $("[class='icon icon_size_m icon_name_calendar icon_theme_alfa-on-white']").click();
        $("[class='calendar__arrow calendar__arrow_direction_right calendar__arrow_double']").click();
        $("[data-day='1695157200000']").click();
        String meetingDate = $("[placeholder='Дата встречи']").getValue();
        $("[name='name']").setValue("Смит Джон");
        $("[name='phone']").setValue("+79999999999");
        $("[data-test-id='agreement']").click();
        $(".button__text").click();
        $(".notification__title").shouldBe(visible, Duration.ofSeconds(15));
        $(".notification__title").shouldHave(exactText("Успешно!"));
        $(".notification__content").shouldHave(exactText("Встреча успешно забронирована на " + meetingDate));
    }
}