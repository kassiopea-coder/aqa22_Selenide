package ru.netology.web;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.security.Key;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

class DeliveryCardTest {

    public String generateDate(int datesToAdd, String pattern) {
        return LocalDate.now().plusDays(datesToAdd).format(DateTimeFormatter.ofPattern(pattern));
    }

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void shouldRegisterWithTrueInform() {
        $("[data-test-id=city] input").setValue("Саратов");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(generateDate(3, "dd.MM.yyyy"));
        $("[data-test-id=name] input").setValue("Александр Иванов");
        $("[data-test-id=phone] input").setValue("+79112345678");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $(withText("Успешно!")).shouldBe(visible, Duration.ofSeconds(15));
        $(".notification__content")
                .shouldHave(text("Встреча успешно забронирована на " + generateDate(3, "dd.MM.yyyy")), Duration.ofSeconds(15))
                .shouldBe(visible);
    }

    @Test
    void shouldRegisterWithInvalidCity() {
        $("[data-test-id=city] input").setValue("Ялта");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(generateDate(3, "dd.MM.yyyy"));
        $("[data-test-id=name] input").setValue("Александр Иванов");
        $("[data-test-id=phone] input").setValue("+79112345678");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();

        String expected = "Доставка в выбранный город недоступна";
        String actual = $(By.cssSelector("[data-test-id=city].input_invalid .input__sub")).getText().trim();
    }

    @Test
    void shouldRegisterWithInvalidDate() {
        $("[data-test-id=city] input").setValue("Саратов");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(generateDate(2, "dd.MM.yyyy"));
        $("[data-test-id=name] input").setValue("Александр Иванов");
        $("[data-test-id=phone] input").setValue("+79112345678");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();

        String expected = "Заказ на выбранную дату невозможен";
        String actual = $(By.cssSelector("[data-test-id=date] .input_invalid .input__sub")).getText().trim();
    }


    @Test
    void shouldRegisterWithInvalidName() {
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(generateDate(3, "dd.MM.yyyy"));
        $("[data-test-id=name] input").setValue("Alex Ivanov");
        $("[data-test-id=phone] input").setValue("+79112345678");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();

        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        String actual = $(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText().trim();
    }

    @Test
    void shouldRegisterWithInvalidPhone() {
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(generateDate(3, "dd.MM.yyyy"));
        $("[data-test-id=name] input").setValue("Александр Иванов");
        $("[data-test-id=phone] input").setValue("+791123456");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();

        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = $(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText().trim();
    }

}



