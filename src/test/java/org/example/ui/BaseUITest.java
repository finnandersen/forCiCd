package org.example.ui;

import com.codeborne.selenide.Configuration;
import io.qameta.allure.Step;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static com.codeborne.selenide.Selenide.closeWebDriver;

/**
 * Базовый класс для UI тестов (локальный Chrome)
 */
public class BaseUITest {

    @BeforeEach
    @Step("Настройка локального Chrome")
    public void setUp() {

        // Локальный браузер
        Configuration.browser = "chrome";

        // Размер окна
        Configuration.browserSize = "1920x1080";

        // Таймауты
        Configuration.timeout = 10_000;
        Configuration.pageLoadTimeout = 30_000;

        // Закрывать браузер после теста
        Configuration.holdBrowserOpen = false;
    }

    @AfterEach
    @Step("Закрытие браузера")
    public void tearDown() {
        closeWebDriver();
    }
}
