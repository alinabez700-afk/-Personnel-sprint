# Персонал+ — автотести продукту «Персонал+» (Команда E, ФБ-45, спринт 4)
Дисципліна «Технології забезпечення якості програмних засобів», командний формат, спринт 4.
Продукт: П3 «Персонал+» (slug `personal`). Demo: https://opensource-demo.orangehrmlive.com. 

## Склад команди і ролі
| ПІБ | GitHub | Роль С4 | Роль С5 |
|-----|--------|---------|---------|
| Безугла Аліна | @alinabez700-afk | Керівник тестування + Скрам-майстер/рецензент |  Тест-аналітик |
| Вилінська Злата | @zlatavylinska-cmd | Інженер з тестування (SDET) | Керівник тестування + Скрам-майстер/рецензент|
| Морквич Юлія | @Yuliamorkvych | Тест-аналітик | Інженер з тестування (SDET) |

## Вимоги до середовища
JDK 17 (Temurin), Maven 3.9, Chrome або Firefox (драйвер ставить Selenium Manager; потрібен інтернет під час першого запуску), JUnit 5.10.2, selenium-java 4.21.0.

## Як зібрати і запустити
```
git clone <URL репозиторію>
cd <папка>
mvn clean test                          # лише швидкі юніт-тести (UI з тегом ui вимкнено)
mvn test -DexcludedGroups=none          # юніт + UI-сценарії WebDriver
mvn test -Dtest=PayrollCalculatorTest   # один клас
```
Звіти Surefire: `target/surefire-reports/`. Підсумок запуску: `docs/sprint4/surefire-summary.md`.
Модуль бізнес-логіки v1 (3 класи) лежить у `src/main/java/ua/kpi/tzyapz/personal/` і не змінюється; виправлення прийдуть у v2.
Очікувано: на v1 частина юніт-тестів червона — це знайдені дефекти модуля (кожен має звіт BUG-007… у docs/sprint4/bug-reports.md).

## Структура
```
src/main/java/ua/kpi/tzyapz/personal/      модуль v1 (LeaveCalculator, PayrollCalculator, EmployeeValidator)
src/test/java/ua/kpi/tzyapz/personal/      юніт-тести JUnit 5: <Клас>Test.java
src/test/java/ua/kpi/tzyapz/personal/ui/   WebDriver-сценарії (@Tag("ui")): LoginUiTest, NavigationUiTest
selenium-ide/personal.side                 проєкт Selenium IDE (2 сценарії); export/ — Java-чернетка
docs/sprint4/                                    automation, coverage-matrix, ui-scenarios, bug-reports, surefire-summary, tasks, retro, review-checklist
```

## Що покрито
| Тест-клас | Вимоги | Методів | Автор |
|-----------|--------|---------|-------|
| LeaveCalculatorTest | BL-10, BL-20, BL-30, BL-40 | 9 | Морквич Юлія |
| PayrollCalculatorTest | BL-50, BL-60, BL-70 | 8 | Вилінська Злата |
| EmployeeValidatorTest | BL-80 | 10 | Безугла Аліна |
| ui/LoginUiTest, ui/NavigationUiTest | TC-001, TC-002, TC-003, TC-006, TC-014, TC-020 (F-10, F-20, F-40, F-90, GUI-10, GUI-30, GUI-40) | 8 | Вилінська Злата |
Повна матриця з межами та рецензентами — `docs/sprint4/coverage-matrix.md`; UI — `docs/sprint4/ui-scenarios.md`.

## Дефекти
Реєстр дефектів ведеться таблицею, як у спринті 3 (GitHub Issues команда не використовує): `docs/sprint4/bug-reports.md`, нумерація продовжує BUG-001…006 → BUG-007… (мітка module). Шаблон і зведення — в тому самому файлі.

## Версії
- v1 — спринт 4: тег `v1-sprint4`, результат `mvn test`: <N passed / M failed> (див. docs/sprint4/surefire-summary.md)
