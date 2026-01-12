
# Настройка Allure отчетов

## Что было добавлено

1. **Зависимости Allure** для JUnit 5
2. **AspectJ** для поддержки аннотаций (@Step, @Attachment)
3. **Настройка компилятора** для сохранения имен параметров
4. **Gradle задачи** для генерации и просмотра отчетов

## Запуск тестов с генерацией Allure результатов

```bash
./gradlew test
```

Результаты будут сохранены в: `build/allure-results/`

## Генерация Allure отчета

После запуска тестов выполните:

```bash
./gradlew allureReport
```

Отчет будет создан в: `build/allure-report/index.html`

## Просмотр отчета в браузере

Для автоматического открытия отчета в браузере:

```bash
./gradlew allureServe
```

Эта команда:
- Запустит тесты (если еще не запущены)
- Сгенерирует отчет
- Откроет его в браузере на `http://localhost:port`

## Требования

Для генерации отчетов нужен **Allure Commandline**. Установите его:

### Windows (через Scoop или Chocolatey):
```bash
# Scoop
scoop install allure

# Chocolatey
choco install allure-commandline
```

### MacOS:
```bash
brew install allure
```

### Linux:
```bash
# Ubuntu/Debian
sudo apt-get install allure

# Или через npm
npm install -g allure-commandline
```

## Использование Allure аннотаций в тестах

Теперь вы можете использовать Allure аннотации в ваших тестах:

```java
import io.qameta.allure.*;
import org.junit.jupiter.api.Test;

@Epic("API Testing")
@Feature("User Management")
public class ApiTests {
    
    @Test
    @Story("Get user by ID")
    @Description("Test getting user by ID endpoint")
    @Severity(SeverityLevel.CRITICAL)
    public void testGetUserById() {
        // ваш тест
    }
    
    @Test
    @Step("Create new user")
    public void testCreateUser() {
        // ваш тест
    }
}
```

## Интеграция с Jenkins

Для Jenkins используйте Jenkinsfile, который уже создан. Он автоматически генерирует Allure отчет при падении stage "Test".

Путь к результатам в Jenkinsfile: `build/allure-results`

## Структура директорий

```
build/
├── allure-results/     # Результаты тестов (JSON файлы)
└── allure-report/      # Сгенерированный HTML отчет
    └── index.html      # Откройте этот файл в браузере
```

## Полезные ссылки

- [Allure Documentation](https://allurereport.org/docs/)
- [Allure JUnit 5 Integration](https://allurereport.org/docs/junit5/)
- [Allure Annotations](https://allurereport.org/docs/gettingstarted/)
