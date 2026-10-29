# Протокол запуску (Surefire) — модуль personal v1

ШАБЛОН: заповнити після реального запуску. Числа тут не вигадані — вставте вивід вашої консолі.

- Дата / машина / JDK: `<дд.мм.рррр>` / `<ОС>` / `java -version`
- Команда 1 (юніт): `mvn clean test` → `Tests run: __, Failures: __, Errors: __, Skipped: 0`
- Команда 2 (усе): `mvn test -DexcludedGroups=none` → `Tests run: __, Failures: __, Errors: 0`
- Звіти: `target/surefire-reports/*.txt`, `TEST-*.xml`

## Червоні тести і дефекти (кожен червоний тест = звіт BUG-NNN)
| Тест (клас.метод[запуск]) | Вимога | Expected / but was | BUG (bug-reports.md) |
|---------------------------|--------|--------------------|-------------|
| `<Клас>Test.<метод>` | BL-NN | `<expected>` / `<but was>` | BUG-007 |

Очікування: на v1 закладено 6 дефектів; усі інші тести зелені; UI-набір — Failures: 0.
