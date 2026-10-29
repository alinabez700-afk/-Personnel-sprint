# Досьє якості v4 — розділ «Автоматизація» (Персонал+, Team_E, спринт 4)

Еталон вимог: SRS 1.1 (спринт 2); тест-кейси TC-001…TC-020, дефекти BUG-001…006 (спринт 3). Автори: Безугла А. (КТ+СМ), Вилінська З. (ІТ), Морквич Ю. (ТА). Команда E (ФБ-45) · Еталон: SRS 1.1 · Репозиторій: https://github.com/alinabez700-afk/-Personnel-sprint

## 1. Норматив (3 особи): план / факт
| Показник | Норматив | Факт |
|----------|----------|------|
| Юніт-тести JUnit 5 (параметризований метод = 1) | ≥12 | 27 (Юлія 9, Злата 8, Аліна 10) |
| UI-сценарії WebDriver (TC-001, 002, 003, 006, 020) | ≥4 | 6 методів / 5 TC |
| Дефекти модуля із 6 закладених (звіт у bug-reports.md + тест) | ≥3 | <заповнити після запуску> |
| Юніт-тестів на особу | ≥3 | 8–10 кожен |
| Сценарії Selenium IDE | 1–2 | 2 (`selenium-ide/personal.side`) |

## 2. Де що лежить
- Код і тести: `src/main/java/ua/kpi/tzyapz/personal/` (модуль v1), `src/test/java/ua/kpi/tzyapz/personal/` (юніт), `.../ui/` (WebDriver), `pom.xml` у корені
- Selenium IDE: `selenium-ide/personal.side`, експорт — `selenium-ide/export/`
- Документи: `docs/sprint4/` — `coverage-matrix.md`, `ui-scenarios.md`, `bug-reports.md`, `surefire-summary.md`, `tasks.md`, `retro.md`, `review-checklist.md`
- Запуск: `mvn clean test` (юніт); `mvn test -DexcludedGroups=none` (юніт + UI)

## 3. Матриця покриття BL — див. `coverage-matrix.md`
## 4. UI-сценарії — див. `ui-scenarios.md`
## 5. Підсумок Surefire — див. `surefire-summary.md` (вставити фактичний вивід)

## 6. Дефекти модуля v1 (docs/sprint4/bug-reports.md, мітка module; BUG-001…006 — demo зі спринта 3, нумерація продовжує BUG-007…)
| BUG | Вимога | Тест | Severity | Звіт |
|-----|--------|------|----------|-------|
| BUG-007 | BL-NN | `<Клас>Test.<метод>` | <Major> | <у bug-reports.md> |


