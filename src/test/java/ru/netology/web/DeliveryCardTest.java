package ru.netology.web;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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


    String generateDate(int datesToAdd, String pattern) {
        return LocalDate.now().plusDays(datesToAdd).format(DateTimeFormatter.ofPattern(pattern));
    }

    @BeforeEach
    void setUp() {

        open("http://localhost:9999");

    }

    @Test
    void shouldRegisterWithTrueInform() {
        //Configuration.holdBrowserOpen = true;


        $("[data-test-id=city] input").setValue("Саратов");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(generateDate(3, "dd.MM.yyyy"));
        $("[data-test-id=name] input").setValue("Александр Иванов");
        $("[data-test-id=phone] input").setValue("+79112345678");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $(withText("Успешно!")).shouldBe(visible, Duration.ofSeconds(15));
    }
}



