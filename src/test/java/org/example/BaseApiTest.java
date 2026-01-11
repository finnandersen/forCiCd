package org.example;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

/**
 * Базовый класс для всех API тестов
 */
public class BaseApiTest {

    protected static final String BASE_URI = "http://85.192.34.140:8080";

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = BASE_URI;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        
        // Можно добавить общие настройки для всех тестов
        // Например, таймауты, прокси и т.д.
    }
}

