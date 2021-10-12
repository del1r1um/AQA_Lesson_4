package ru.netology.carddelivery;

import org.junit.jupiter.api.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class TestCardDeliveryWithDropDownElements {
    LocalDate meetingDate = LocalDate.now().plusDays(21);
    DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void shouldSendFormUsingDropDownElements() {
        $("[data-test-id='city'] .input__control").setValue("Мо");
        $$(".menu-item").find(exactText("Москва")).click();
        $(".icon-button").click();
        if ((meetingDate.getMonthValue() != (LocalDate.now()).getMonthValue())) {
            $("[data-step='1']").click();
        }
        $(byText(String.valueOf(meetingDate.getDayOfMonth()))).click();
        $("[data-test-id='name'] .input__control").setValue("Сергей Иванов-Петров");
        $("[data-test-id='phone'] .input__control").setValue("+79031234567");
        $(".checkbox__box").click();
        $(".button__text").click();
        $(".notification").shouldBe(appear, Duration.ofSeconds(15));
        $("[data-test-id='notification'] .notification__content")
                .shouldHave(text("Встреча успешно забронирована на " + formatDate.format(meetingDate)));
    }
}