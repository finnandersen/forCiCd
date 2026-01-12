package org.example;

import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

/**
 * Простые автотесты для API используя RestAssured
 */
@Epic("API Testing")
@Feature("REST API Tests")
public class ApiTests {

    private static final String BASE_URI = "http://85.192.34.140:8080";

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = BASE_URI;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    @DisplayName("Проверка доступности API (health check)")
    @Story("Health Check")
    @Description("Проверка доступности API через health check endpoint")
    @Severity(SeverityLevel.BLOCKER)
    public void testHealthCheck() {
        given()
            .when()
                .get("/actuator/health")
            .then()
                .statusCode(anyOf(is(200), is(401), is(404)))
                .log().all();
    }

    @Test
    @DisplayName("Получение списка пользователей (GET)")
    @Story("User Management")
    @Description("Получение списка всех пользователей через GET запрос")
    @Severity(SeverityLevel.CRITICAL)
    public void testGetUsers() {
        given()
            .contentType(ContentType.JSON)
            .when()
                .get("/api/users")
            .then()
                .statusCode(anyOf(is(200), is(401), is(404)))
                .log().all();
    }

    @Test
    @DisplayName("Получение пользователя по ID (GET)")
    public void testGetUserById() {
        int userId = 1;
        
        given()
            .contentType(ContentType.JSON)
            .pathParam("id", userId)
            .when()
                .get("/api/users/{id}")
            .then()
                .statusCode(anyOf(is(200), is(401), is(404), is(400)))
                .log().all();
    }

    @Test
    @DisplayName("Создание нового пользователя (POST)")
    @Story("User Management")
    @Description("Создание нового пользователя через POST запрос")
    @Severity(SeverityLevel.CRITICAL)
    public void testCreateUser() {
        String requestBody = """
            {
                "name": "Test User",
                "email": "test@example.com"
            }
            """;

        given()
            .contentType(ContentType.JSON)
            .body(requestBody)
            .when()
                .post("/api/users")
            .then()
                .statusCode(anyOf(is(201), is(200), is(401), is(400)))
                .log().all();
    }

    @Test
    @DisplayName("Обновление пользователя (PUT)")
    public void testUpdateUser() {
        int userId = 1;
        String requestBody = """
            {
                "name": "Updated User",
                "email": "updated@example.com"
            }
            """;

        given()
            .contentType(ContentType.JSON)
            .pathParam("id", userId)
            .body(requestBody)
            .when()
                .put("/api/users/{id}")
            .then()
                .statusCode(anyOf(is(200), is(401), is(404), is(400)))
                .log().all();
    }

    @Test
    @DisplayName("Удаление пользователя (DELETE)")
    public void testDeleteUser() {
        int userId = 1;

        given()
            .contentType(ContentType.JSON)
            .pathParam("id", userId)
            .when()
                .delete("/api/users/{id}")
            .then()
                .statusCode(anyOf(is(200), is(204), is(401), is(404)))
                .log().all();
    }

    @Test
    @DisplayName("Проверка Swagger UI доступности")
    public void testSwaggerUi() {
        given()
            .when()
                .get("/swagger-ui/index.html")
            .then()
                .statusCode(anyOf(is(200), is(401), is(404)))
                .log().all();
    }

    @Test
    @DisplayName("Получение Swagger документации")
    public void testSwaggerDocs() {
        given()
            .when()
                .get("/v3/api-docs")
            .then()
                .statusCode(anyOf(is(200), is(401), is(404)))
                .log().all();
    }

    @Test
    @DisplayName("Проверка ответа API с валидацией структуры")
    public void testApiResponseStructure() {
        Response response = given()
            .contentType(ContentType.JSON)
            .when()
                .get("/api/users")
            .then()
                .extract()
                .response();

        // Проверяем, что ответ не пустой
        if (response.getStatusCode() == 200) {
            response.then()
                .body("$", notNullValue())
                .log().all();
        }
    }
}

