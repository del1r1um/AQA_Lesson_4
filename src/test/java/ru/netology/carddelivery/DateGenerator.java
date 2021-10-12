package ru.netology.carddelivery;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateGenerator {
    public static String generateDay(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }
}