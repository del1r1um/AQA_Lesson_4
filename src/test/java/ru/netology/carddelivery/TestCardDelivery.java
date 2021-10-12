package ru.netology.carddelivery;

import org.junit.jupiter.api.*;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static ru.netology.carddelivery.DateGenerator.generateDay;

public class TestCardDelivery {
    String meetingDate = generateDay(7);

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void shouldSendFormWithValidData() {
        $("[data-test-id='city'] .input__control").setValue("Москва");
        $("[data-test-id='date'] .input__control").doubleClick().sendKeys(Keys.DELETE, meetingDate);
        $("[data-test-id='name'] .input__control").setValue("Сергей Сергеев");
        $("[data-test-id='phone'] .input__control").setValue("+79031234567");
        $(".checkbox__box").click();
        $(".button__text").click();
        $(".notification").shouldBe(appear, Duration.ofSeconds(15));
        $("[data-test-id='notification'] .notification__content")
                .shouldHave(text("Встреча успешно забронирована на " + meetingDate));
    }

    @Test
    void shouldSendFormWithComplexName() {
        $("[data-test-id='city'] .input__control").setValue("Москва");
        $("[data-test-id='date'] .input__control").doubleClick().sendKeys(Keys.DELETE, meetingDate);
        $("[data-test-id='name'] .input__control").setValue("Ирина Иванова-Смирнова");
        $("[data-test-id='phone'] .input__control").setValue("+79031234567");
        $(".checkbox__box").click();
        $(".button__text").click();
        $(".notification").shouldBe(appear, Duration.ofSeconds(15));
        $("[data-test-id='notification'] .notification__content")
                .shouldHave(text("Встреча успешно забронирована на " + meetingDate));
    }

    @Test
    void shouldSendFormWithEmptyNameField() {
        $("[data-test-id='city'] .input__control").setValue("Москва");
        $("[data-test-id='date'] .input__control").doubleClick().sendKeys(Keys.DELETE, meetingDate);
        $("[data-test-id='phone'] .input__control").setValue("+79001234567");
        $(".checkbox__box").click();
        $(".button__text").click();
        $("[data-test-id='name'].input_invalid")
                .shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    void shouldSendFormWithNotCyrillicName() {
        $("[data-test-id='city'] .input__control").setValue("Москва");
        $("[data-test-id='date'] .input__control").doubleClick().sendKeys(Keys.DELETE, meetingDate);
        $("[data-test-id='name'] .input__control").setValue("Ivan Ivanov");
        $("[data-test-id='phone'] .input__control").setValue("+79031234567");
        $(".checkbox__box").click();
        $(".button__text").click();
        $("[data-test-id='name'].input_invalid")
                .shouldHave(text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldSendFormWithNumbersInNameField() {
        $("[data-test-id='city'] .input__control").setValue("Москва");
        $("[data-test-id='date'] .input__control").doubleClick().sendKeys(Keys.DELETE, meetingDate);
        $("[data-test-id='name'] .input__control").setValue("1234567");
        $("[data-test-id='phone'] .input__control").setValue("+79031234567");
        $(".checkbox__box").click();
        $(".button__text").click();
        $("[data-test-id='name'].input_invalid")
                .shouldHave(text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldSendFormWithEmptyCityField() {
        $("[data-test-id='date'] .input__control").doubleClick().sendKeys(Keys.DELETE, meetingDate);
        $("[data-test-id='name'] .input__control").setValue("Юлия Смирнова");
        $("[data-test-id='phone'] .input__control").setValue("+79031234567");
        $(".checkbox__box").click();
        $(".button__text").click();
        $("[data-test-id='city'].input_invalid").shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    void shouldSendFormWithInvalidCity() {
        $("[data-test-id='city'] .input__control").setValue("Мытищи");
        $("[data-test-id='date'] .input__control").doubleClick().sendKeys(Keys.DELETE, meetingDate);
        $("[data-test-id='name'] .input__control").setValue("Владимир Владимирский");
        $("[data-test-id='phone'] .input__control").setValue("+79031234567");
        $(".checkbox__box").click();
        $(".button__text").click();
        $("[data-test-id='city'].input_invalid").shouldHave(text("Доставка в выбранный город недоступна"));
    }

    @Test
    void shouldSendFormWithNumbersInCityField() {
        $("[data-test-id='city'] .input__control").setValue("1234567");
        $("[data-test-id='date'] .input__control").doubleClick().sendKeys(Keys.DELETE, meetingDate);
        $("[data-test-id='name'] .input__control").setValue("Аглая Демиденко");
        $("[data-test-id='phone'] .input__control").setValue("+79031234567");
        $(".checkbox__box").click();
        $(".button__text").click();
        $("[data-test-id='city'].input_invalid").shouldHave(text("Доставка в выбранный город недоступна"));
    }

    @Test
    void shouldSendFormWithEmptyPhoneNumberField() {
        $("[data-test-id='city'] .input__control").setValue("Москва");
        $("[data-test-id='date'] .input__control").doubleClick().sendKeys(Keys.DELETE, meetingDate);
        $("[data-test-id='name'] .input__control").setValue("Никанор Никаноров");
        $(".checkbox__box").click();
        $(".button__text").click();
        $("[data-test-id='phone'].input_invalid").shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    void shouldSendFormWithInvalidPhoneNumber() {
        $("[data-test-id='city'] .input__control").setValue("Москва");
        $("[data-test-id='date'] .input__control").doubleClick().sendKeys(Keys.DELETE, meetingDate);
        $("[data-test-id='name'] .input__control").setValue("Илья Ильясов");
        $("[data-test-id='phone'] .input__control").setValue("+790312345678");
        $(".checkbox__box").click();
        $(".button__text").click();
        $("[data-test-id='phone'].input_invalid")
                .shouldHave(text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldSendFormWithLettersInPhoneNumberField() {
        $("[data-test-id='city'] .input__control").setValue("Москва");
        $("[data-test-id='date'] .input__control").doubleClick().sendKeys(Keys.DELETE, meetingDate);
        $("[data-test-id='name'] .input__control").setValue("Ольга Сабо");
        $("[data-test-id='phone'] .input__control").setValue("abcde");
        $(".checkbox__box").click();
        $(".button__text").click();
        $("[data-test-id='phone'].input_invalid")
                .shouldHave(text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldSendFormWithEmptyDateField() {
        $("[data-test-id='city'] .input__control").setValue("Москва");
        $("[data-test-id='date'] .input__control").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id='name'] .input__control").setValue("Евгения Жукова");
        $("[data-test-id='phone'] .input__control").setValue("+79031234567");
        $(".checkbox__box").click();
        $(".button__text").click();
        $("[data-test-id='date'] .input_invalid").shouldHave(text("Неверно введена дата"));
    }

    @Test
    void shouldSendFormWithInvalidDate() {
        String meetingDate = generateDay(1);
        $("[data-test-id='city'] .input__control").setValue("Москва");
        $("[data-test-id='date'] .input__control").doubleClick().sendKeys(Keys.DELETE, meetingDate);
        $("[data-test-id='name'] .input__control").setValue("Евгения Жукова");
        $("[data-test-id='phone'] .input__control").setValue("+79031234567");
        $(".checkbox__box").click();
        $(".button__text").click();
        $("[data-test-id='date'] .input_invalid").shouldHave(text("Заказ на выбранную дату невозможен"));
    }

    @Test
    void shouldSendFormWithNotCheckedAgreement() {
        $("[data-test-id='city'] .input__control").setValue("Москва");
        $("[data-test-id='date'] .input__control").doubleClick().sendKeys(Keys.DELETE, meetingDate);
        $("[data-test-id='name'] .input__control").setValue("Владислав Владимиров");
        $("[data-test-id='phone'] .input__control").setValue("+79031234567");
        $(".button__text").click();
        $("[data-test-id='agreement'].input_invalid")
                .shouldHave(text("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
    }
}