package org.example;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.BeforeAll;

import static org.hamcrest.Matchers.lessThan;

/**
 * Конфигурационный класс для настройки RestAssured
 */
public class ApiTestConfig {

    protected static RequestSpecification requestSpec;
    protected static ResponseSpecification responseSpec;

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://85.192.34.140:8080";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        // Настройка спецификации запроса
        requestSpec = new RequestSpecBuilder()
                .setBaseUri("http://85.192.34.140:8080")
                .setContentType(ContentType.JSON)
                .addHeader("Accept", "application/json")
                .build();

        // Настройка спецификации ответа
        responseSpec = new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectResponseTime(lessThan(5000L)) // Response should come in less than 5 seconds
                .build();

        RestAssured.requestSpecification = requestSpec;
        RestAssured.responseSpecification = responseSpec;
    }
}

