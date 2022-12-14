package ru.netology.carddelivery;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
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
        String meetingDate = generateDate(4);
        $("[placeholder='Город']").setValue("Москва");
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(Keys.DELETE + meetingDate);
        $("[name='name']").setValue("Смит Джон-Джуниор");
        $("[name='phone']").setValue("+79999999999");
        $("[data-test-id='agreement']").click();
        $("[type='button'] .button__text ").click();
        $(".notification__title").shouldBe(visible, Duration.ofSeconds(15));
        $(".notification__title").shouldHave(exactText("Успешно!"));
        $(".notification__content").shouldHave(exactText("Встреча успешно забронирована на " + meetingDate));
    }

    @Test
    void shouldTestDropdowns() {
        $("[placeholder='Город']").setValue("Мо");
        $(".input__popup>div>div>div>div>div:nth-child(3)").click();
        $(".icon.icon_size_m.icon_name_calendar.icon_theme_alfa-on-white").click();
        $(".calendar__day.calendar__day_state_current").click();
        String meetingDate = $("[placeholder='Дата встречи']").getValue();
        $("[name='name']").setValue("Смит Джон");
        $("[name='phone']").setValue("+79999999999");
        $("[data-test-id='agreement']").click();
        $("[type='button'] .button__text ").click();
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
        $x("//tbody/tr[5]/td[3]").click();
        String meetingDate = $("[placeholder='Дата встречи']").getValue();
        $("[name='name']").setValue("Смит Джон");
        $("[name='phone']").setValue("+79999999999");
        $("[data-test-id='agreement']").click();
        $("[type='button'] .button__text ").click();
        $(".notification__title").shouldBe(visible, Duration.ofSeconds(15));
        $(".notification__title").shouldHave(exactText("Успешно!"));
        $(".notification__content").shouldHave(exactText("Встреча успешно забронирована на " + meetingDate));
    }

    @Test
    void shouldTestCalendarDateNextYear() {
        $("[placeholder='Город']").setValue("Юж");
        $("[class='menu-item__control']").click();
        $("[class='icon icon_size_m icon_name_calendar icon_theme_alfa-on-white']").click();
        $("[class='calendar__arrow calendar__arrow_direction_right calendar__arrow_double']").click();
        $x("//tbody/tr[5]/td[4]").click();
        String meetingDate = $("[placeholder='Дата встречи']").getValue();
        $("[name='name']").setValue("Смит Джон");
        $("[name='phone']").setValue("+79999999999");
        $("[data-test-id='agreement']").click();
        $("[type='button'] .button__text ").click();
        $(".notification__title").shouldBe(visible, Duration.ofSeconds(15));
        $(".notification__title").shouldHave(exactText("Успешно!"));
        $(".notification__content").shouldHave(exactText("Встреча успешно забронирована на " + meetingDate));
    }
}