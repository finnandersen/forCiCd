# UI тесты с Selenoid

## Что было добавлено

1. **Selenide зависимости** в `build.gradle`
2. **Базовый класс `BaseUITest`** с настройкой Selenoid
3. **5 UI тестов** для сайта the-internet.herokuapp.com
4. **Альтернативные тесты** для demoqa.com

## Запуск Selenoid

### Вариант 1: Через Docker Compose

Создайте файл `docker-compose.yml`:

```yaml
version: '3'
services:
  selenoid:
    image: aerokube/selenoid:latest-release
    container_name: selenoid
    ports:
      - "4444:4444"
    volumes:
      - "$PWD:/etc/selenoid"
      - "/var/run/docker.sock:/var/run/docker.sock"
    command: ["-conf", "/etc/selenoid/browsers.json", "-limit", "10"]
  
  selenoid-ui:
    image: aerokube/selenoid-ui:latest-release
    container_name: selenoid-ui
    ports:
      - "8080:8080"
    depends_on:
      - selenoid
    command: ["--selenoid-uri", "http://selenoid:4444"]
```

### Вариант 2: Через Docker напрямую

```bash
# Запуск Selenoid
docker run -d \
  --name selenoid \
  -p 4444:4444 \
  -v /var/run/docker.sock:/var/run/docker.sock \
  -v $PWD:/etc/selenoid \
  aerokube/selenoid:latest-release \
  -conf /etc/selenoid/browsers.json \
  -limit 10

# Запуск Selenoid UI (опционально, для мониторинга)
docker run -d \
  --name selenoid-ui \
  -p 8080:8080 \
  aerokube/selenoid-ui:latest-release \
  --selenoid-uri http://localhost:4444
```

## Конфигурация browsers.json

Создайте файл `browsers.json` в корне проекта:

```json
{
  "chrome": {
    "default": "latest",
    "versions": {
      "latest": {
        "image": "selenoid/chrome:latest",
        "port": "4444",
        "path": "/"
      }
    }
  },
  "firefox": {
    "default": "latest",
    "versions": {
      "latest": {
        "image": "selenoid/firefox:latest",
        "port": "4444",
        "path": "/"
      }
    }
  }
}
```

## Запуск тестов

### Все UI тесты:
```bash
./gradlew test --tests "org.example.ui.*"
```

### Конкретный класс тестов:
```bash
./gradlew test --tests "org.example.ui.InternetHeroAppTests"
```

### Конкретный тест:
```bash
./gradlew test --tests "org.example.ui.InternetHeroAppTests.testHomePageTitle"
```

## Настройка для локального запуска (без Selenoid)

Если хотите запускать тесты локально без Selenoid, измените `BaseUITest.java`:

```java
@BeforeEach
public void setUp() {
    // Уберите или закомментируйте строку с remote
    // Configuration.remote = "http://localhost:4444/wd/hub";
    
    Configuration.browser = "chrome";
    Configuration.browserSize = "1920x1080";
    // остальные настройки...
}
```

## Просмотр выполнения тестов

1. **Selenoid UI**: Откройте http://localhost:8080 для мониторинга сессий
2. **VNC**: Если включен VNC, можно подключиться через VNC клиент
3. **Видео**: Видео записываются автоматически (если включено)

## Структура тестов

```
src/test/java/org/example/ui/
├── BaseUITest.java              # Базовый класс с настройкой Selenoid
├── InternetHeroAppTests.java    # 5 тестов для the-internet.herokuapp.com
└── DemoQATests.java            # Альтернативные тесты для demoqa.com
```

## Тесты в InternetHeroAppTests

1. **testHomePageTitle** - Проверка заголовка главной страницы
2. **testLoginPageLink** - Проверка перехода на страницу логина
3. **testDynamicContent** - Проверка динамического контента
4. **testCheckboxes** - Проверка работы чекбоксов
5. **testDropdown** - Проверка работы выпадающего списка

## Полезные ссылки

- [Selenide Documentation](https://selenide.org/)
- [Selenoid Documentation](https://aerokube.com/selenoid/)
- [The Internet Hero App](https://the-internet.herokuapp.com)
- [DemoQA](https://demoqa.com)

## Troubleshooting

### Проблема: Не может подключиться к Selenoid

**Решение**: Убедитесь, что Selenoid запущен:
```bash
docker ps | grep selenoid
```

### Проблема: Тесты падают с таймаутом

**Решение**: Увеличьте таймауты в `BaseUITest.java`:
```java
Configuration.timeout = 20000;
Configuration.pageLoadTimeout = 60000;
```

### Проблема: Браузер не запускается

**Решение**: Проверьте конфигурацию `browsers.json` и убедитесь, что образы браузеров скачаны:
```bash
docker pull selenoid/chrome:latest
docker pull selenoid/firefox:latest
```
