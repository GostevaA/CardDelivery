package ru.netology.CardDelivery;


import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.$;

public class CardTest {

    @BeforeEach
    void open() {

        Selenide.open("http://localhost:9999");
    }

    public String generateDate(int addDays) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @Test
    public void shouldBeSuccessfullyCompleted() {
        $("[data-test-id = city] input").setValue("Казань");
        String currentDate = generateDate(7);
        $("[data-test-id = date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id = date] input").sendKeys(currentDate);
        $("[data-test-id = name] .input__control").setValue("Иван Петров");
        $("[data-test-id = phone] .input__control").setValue("+79000000000");
        $("[data-test-id = agreement]").click();
        $("button.button").click();
        $(".notification__content")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Встреча успешно забронирована на " + currentDate));


    }

}