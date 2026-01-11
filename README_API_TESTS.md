# API Автотесты на RestAssured

Набор простых автотестов для API `http://85.192.34.140:8080` используя RestAssured и JUnit 5.

## Структура проекта

```
src/test/java/org/example/
├── ApiTests.java           - Основные тесты API (GET, POST, PUT, DELETE)
├── ApiTestWithAuth.java    - Тесты с различными типами аутентификации
├── ApiTestConfig.java      - Конфигурация RestAssured
└── BaseApiTest.java        - Базовый класс для тестов
```

## Зависимости

Проект использует:
- **RestAssured 5.4.0** - для работы с REST API
- **JUnit 5** - для запуска тестов
- **Hamcrest** - для проверок (assertions)

## Запуск тестов

### Через Gradle:
```bash
./gradlew test
```

### Через IDE:
Запустите тесты через IntelliJ IDEA или другую IDE

### Запуск конкретного теста:
```bash
./gradlew test --tests ApiTests
```

## Настройка тестов

### Базовый URI
Все тесты используют базовый URI: `http://85.192.34.140:8080`

Если нужно изменить, отредактируйте константу `BASE_URI` в классах тестов.

### Аутентификация
В файле `ApiTestWithAuth.java` есть примеры различных типов аутентификации:
- Basic Authentication
- Bearer Token
- API Key
- Login endpoint для получения токена

**Важно:** Замените значения `USERNAME`, `PASSWORD`, `token` и `apiKey` на реальные данные из вашего API.

## Примеры тестов

### GET запрос
```java
given()
    .contentType(ContentType.JSON)
    .when()
        .get("/api/users")
    .then()
        .statusCode(200)
        .log().all();
```

### POST запрос
```java
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
        .statusCode(201);
```

### PUT запрос
```java
given()
    .contentType(ContentType.JSON)
    .pathParam("id", userId)
    .body(requestBody)
    .when()
        .put("/api/users/{id}")
    .then()
        .statusCode(200);
```

### DELETE запрос
```java
given()
    .contentType(ContentType.JSON)
    .pathParam("id", userId)
    .when()
        .delete("/api/users/{id}")
    .then()
        .statusCode(204);
```

## Адаптация под ваш API

1. **Изучите Swagger документацию** по адресу: `http://85.192.34.140:8080/swagger-ui/index.html#/`
2. **Определите реальные эндпоинты** и замените `/api/users` на ваши
3. **Настройте структуру JSON** в запросах под ваш API
4. **Добавьте аутентификацию** если требуется
5. **Настройте проверки** (assertions) под структуру ответов вашего API

## Полезные ссылки

- [RestAssured Documentation](https://rest-assured.io/)
- [JUnit 5 Documentation](https://junit.org/junit5/docs/current/user-guide/)
- [Hamcrest Matchers](http://hamcrest.org/JavaHamcrest/javadoc/2.2/)

