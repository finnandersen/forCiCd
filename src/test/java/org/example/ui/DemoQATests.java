package org.example.ui;

import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Альтернативные UI тесты для сайта https://demoqa.com
 * Если the-internet.herokuapp.com недоступен, можно использовать эти тесты
 */
@Epic("UI Testing")
@Feature("DemoQA")
public class DemoQATests extends BaseUITest {

    private static final String BASE_URL = "https://demoqa.com";

    @Test
    @DisplayName("Тест 1: Проверка главной страницы DemoQA")
    @Story("Home Page")
    @Description("Открытие главной страницы и проверка элементов")
    @Severity(SeverityLevel.CRITICAL)
    public void testDemoQAHomePage() {
        open(BASE_URL);
        
        // Проверяем заголовок
        assertTrue(title().contains("DEMOQA"), "Заголовок должен содержать 'DEMOQA'");
        
        // Проверяем наличие карточек с элементами
        $(".card").shouldBe(visible);
        
        // Проверяем наличие нескольких карточек
        assertTrue($$(".card").size() >= 5, "Должно быть минимум 5 карточек");
    }

    @Test
    @DisplayName("Тест 2: Проверка страницы Elements")
    @Story("Elements Page")
    @Description("Переход на страницу Elements и проверка элементов")
    @Severity(SeverityLevel.NORMAL)
    public void testElementsPage() {
        open(BASE_URL + "/elements");
        
        // Проверяем наличие меню элементов
        $(".element-list").shouldBe(visible);
        
        // Проверяем наличие различных разделов
        $$(".btn").first().shouldBe(visible);
    }

    @Test
    @DisplayName("Тест 3: Проверка Text Box формы")
    @Story("Forms")
    @Description("Заполнение и отправка формы Text Box")
    @Severity(SeverityLevel.NORMAL)
    public void testTextBoxForm() {
        open(BASE_URL + "/text-box");
        
        // Заполняем форму
        $("#userName").setValue("Test User");
        $("#userEmail").setValue("test@example.com");
        $("#currentAddress").setValue("123 Test Street");
        $("#permanentAddress").setValue("456 Permanent Street");
        
        // Отправляем форму
        $("#submit").click();
        
        // Проверяем результат
        $("#output").shouldBe(visible);
        $("#output").shouldHave(text("Test User"));
        $("#output").shouldHave(text("test@example.com"));
    }

    @Test
    @DisplayName("Тест 4: Проверка Checkbox")
    @Story("Forms")
    @Description("Проверка работы чекбоксов")
    @Severity(SeverityLevel.NORMAL)
    public void testCheckbox() {
        open(BASE_URL + "/checkbox");
        
        // Раскрываем дерево чекбоксов
        $(".rct-icon-expand-close").click();
        
        // Выбираем чекбокс
        $("label[for='tree-node-desktop']").click();
        
        // Проверяем результат
        $("#result").shouldBe(visible);
        $("#result").shouldHave(text("desktop"));
    }

    @Test
    @DisplayName("Тест 5: Проверка Radio Button")
    @Story("Forms")
    @Description("Проверка работы радио кнопок")
    @Severity(SeverityLevel.NORMAL)
    public void testRadioButton() {
        open(BASE_URL + "/radio-button");
        
        // Выбираем радио кнопку "Yes"
        $("label[for='yesRadio']").click();
        
        // Проверяем результат
        $(".text-success").shouldBe(visible);
        $(".text-success").shouldHave(text("Yes"));
        
        // Выбираем другую радио кнопку
        $("label[for='impressiveRadio']").click();
        $(".text-success").shouldHave(text("Impressive"));
    }
}
