package org.example.ui;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * UI тесты для сайта https://the-internet.herokuapp.com
 * Сайт специально создан для тестирования различных UI элементов
 */
@Epic("UI Testing")
@Feature("The Internet Hero App")
public class InternetHeroAppTests extends BaseUITest {

    private static final String BASE_URL = "https://the-internet.herokuapp.com";

    @Test
    @DisplayName("Тест 1: Проверка заголовка главной страницы")
    @Story("Home Page")
    @Description("Открытие главной страницы и проверка наличия заголовка")
    @Severity(SeverityLevel.CRITICAL)
    public void testHomePageTitle() {
        open(BASE_URL);
        
        // Проверяем заголовок страницы
        assertTrue(title().contains("The Internet"), "Заголовок должен содержать 'The Internet'");
        
        // Проверяем наличие основного заголовка на странице
        $("h1").shouldHave(text("Welcome to the-internet"));
        
        // Проверяем наличие подзаголовка
        $("h2").shouldBe(visible);
    }

    @Test
    @DisplayName("Тест 2: Проверка ссылки на страницу Login")
    @Story("Navigation")
    @Description("Проверка перехода на страницу авторизации")
    @Severity(SeverityLevel.NORMAL)
    public void testLoginPageLink() {
        open(BASE_URL);
        
        // Находим и кликаем на ссылку "Form Authentication"
        $("a[href='/login']").shouldBe(visible).click();
        
        // Проверяем, что перешли на страницу логина
        assertTrue(title().contains("The Internet"), "Заголовок должен содержать 'The Internet'");
        
        // Проверяем наличие формы логина
        $("#username").shouldBe(visible);
        $("#password").shouldBe(visible);
        $("button[type='submit']").shouldBe(visible);
    }

    @Test
    @DisplayName("Тест 3: Проверка динамического контента")
    @Story("Dynamic Content")
    @Description("Проверка загрузки динамического контента")
    @Severity(SeverityLevel.NORMAL)
    public void testDynamicContent() {
        open(BASE_URL + "/dynamic_content");
        
        // Проверяем наличие динамического контента
        $(".large-12").shouldBe(visible);
        
        // Проверяем, что контент загрузился (должно быть минимум 3 блока)
        assertTrue($$(".large-12 .row").size() >= 3, "Должно быть минимум 3 блока контента");
        
        // Проверяем наличие изображений
        assertTrue($$("img").size() >= 3, "Должно быть минимум 3 изображения");
    }

    @Test
    @DisplayName("Тест 4: Проверка Checkboxes")
    @Story("Form Elements")
    @Description("Проверка работы чекбоксов")
    @Severity(SeverityLevel.NORMAL)
    public void testCheckboxes() {
        open(BASE_URL + "/checkboxes");
        
        // Находим все чекбоксы
        SelenideElement checkbox1 = $$("input[type='checkbox']").first();
        SelenideElement checkbox2 = $$("input[type='checkbox']").last();
        
        // Проверяем начальное состояние первого чекбокса
        boolean initialState1 = checkbox1.isSelected();
        
        // Кликаем на первый чекбокс
        checkbox1.click();
        
        // Проверяем, что состояние изменилось
        assertTrue(checkbox1.isSelected() != initialState1, 
            "Состояние первого чекбокса должно измениться");
        
        // Проверяем второй чекбокс
        boolean initialState2 = checkbox2.isSelected();
        checkbox2.click();
        assertTrue(checkbox2.isSelected() != initialState2, 
            "Состояние второго чекбокса должно измениться");
    }

    @Test
    @DisplayName("Тест 5: Проверка Dropdown списка")
    @Story("Form Elements")
    @Description("Проверка работы выпадающего списка")
    @Severity(SeverityLevel.NORMAL)
    public void testDropdown() {
        open(BASE_URL + "/dropdown");
        
        // Находим dropdown
        SelenideElement dropdown = $("#dropdown");
        dropdown.shouldBe(visible);
        
        // Выбираем первый вариант
        dropdown.selectOption(1);
        dropdown.shouldHave(value("1"));
        
        // Выбираем второй вариант
        dropdown.selectOption(2);
        dropdown.shouldHave(value("2"));
        
        // Проверяем, что выбранный вариант отображается
        // Проверяем через видимый текст выбранного option
        $("option[value='2']").shouldBe(selected);
        // Альтернативно можно проверить через текст элемента
        assertTrue($("#dropdown option[selected]").getText().contains("Option 2"), 
            "Должен быть выбран Option 2");
    }
}
