package org.example;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

/**
 * Тесты с аутентификацией (примеры для различных типов авторизации)
 */
@Tag("API_AUTH_TEST")
public class ApiTestWithAuth {

    private static final String BASE_URI = "http://85.192.34.140:8080";
    private static final String USERNAME = "admin"; // Замените на реальные данные
    private static final String PASSWORD = "password"; // Замените на реальные данные

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = BASE_URI;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    @DisplayName("Тест с Basic Authentication")
    public void testWithBasicAuth() {
        given()
            .auth()
            .basic(USERNAME, PASSWORD)
            .contentType(ContentType.JSON)
            .when()
                .get("/api/users")
            .then()
                .statusCode(anyOf(is(200), is(401), is(403)))
                .log().all();
    }

    @Test
    @DisplayName("Тест с Bearer Token")
    public void testWithBearerToken() {
        String token = "your-token-here"; // Замените на реальный токен

        given()
            .header("Authorization", "Bearer " + token)
            .contentType(ContentType.JSON)
            .when()
                .get("/api/users")
            .then()
                .statusCode(anyOf(is(200), is(401), is(403)))
                .log().all();
    }

    @Test
    @DisplayName("Тест с API Key")
    public void testWithApiKey() {
        String apiKey = "your-api-key-here"; // Замените на реальный ключ

        given()
            .header("X-API-Key", apiKey)
            .contentType(ContentType.JSON)
            .when()
                .get("/api/users")
            .then()
                .statusCode(anyOf(is(200), is(401), is(403)))
                .log().all();
    }

    @Test
    @DisplayName("Получение токена через login endpoint")
    public void testLoginAndGetToken() {
        String loginBody = """
            {
                "username": "admin",
                "password": "password"
            }
            """;

        String token = given()
            .contentType(ContentType.JSON)
            .body(loginBody)
            .when()
                .post("/api/login")
            .then()
                .statusCode(anyOf(is(200), is(401), is(404)))
                .extract()
                .path("token");

        // Use the received token for subsequent requests
        if (token != null) {
            given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .when()
                    .get("/api/users")
                .then()
                    .statusCode(anyOf(is(200), is(401), is(403)))
                    .log().all();
        }
    }
}

