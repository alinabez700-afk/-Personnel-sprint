# Досьє якості v4 — розділ «Автоматизація» (Персонал+, Team_E, спринт 4)

Еталон вимог: SRS 1.1 (спринт 2); тест-кейси TC-001…TC-020, дефекти BUG-001…006 (спринт 3). Автори: Безугла А. (КТ+СМ), Вилінська З. (ІТ), Морквич Ю. (ТА). Команда E (ФБ-45). Репозиторій (код і документи — в одному): https://github.com/alinabez700-afk/-Personnel-sprint

## 1. Норматив (3 особи): план / факт
Колонка «Факт» відповідає файлам у репозиторії; дефекти — за прогоном mvn clean test від 19.11.2026 (72 запуски, 13 червоних; з UI — 80 запусків, ті самі 13).
| Показник | Норматив | Факт |
|----------|----------|------|
| Юніт-тести JUnit 5 (параметризований метод = 1) | ≥12 | 27 (Юлія 9, Злата 8, Аліна 10) |
| UI-сценарії WebDriver | ≥4 | 8 методів / 6 TC (TC-001, 002, 003, 006, 014, 020) |
| Дефекти модуля із 6 закладених (звіт у bug-reports.md + тест) | ≥3 | 6 (BUG-007…012) |
| Юніт-тестів на особу | ≥3 | 8–10 кожен |
| Сценарії Selenium IDE | 1–2 | 2 (`selenium-ide/personal.side`, експорт — `selenium-ide/export/`) |

## 2. Де що лежить
- Код і тести: `src/main/java/ua/kpi/tzyapz/personal/` (модуль v1), `src/test/java/ua/kpi/tzyapz/personal/` (юніт), `.../ui/` (WebDriver), `pom.xml` у корені
- Selenium IDE: `selenium-ide/personal.side`, експорт — `selenium-ide/export/`
- Документи: `docs/sprint4/` — `coverage-matrix.md`, `ui-scenarios.md`, `bug-reports.md`, `surefire-summary.md`, `tasks.md`, `retro.md`, `review-checklist.md`
- Запуск: `mvn clean test` (юніт); `mvn test -DexcludedGroups=none` (юніт + UI)

## 3. Матриця покриття BL — див. `coverage-matrix.md`
## 4. UI-сценарії — див. `ui-scenarios.md`
## 5. Підсумок Surefire — див. `surefire-summary.md`
`mvn clean test`: Tests run: 72, Failures: 13, Errors: 0, Skipped: 0 (13 червоних запусків = закладені дефекти v1). 

## 6. Дефекти модуля v1 (docs/sprint4/bug-reports.md, мітка module; BUG-001…006 — demo зі спринта 3)
| BUG | Вимога | Тест | Severity | Звіт |
|-----|--------|------|----------|------|
| BUG-007 | BL-10 | LeaveCalculatorTest.accruedIsTwoPerMonthCappedAt24 | Major | bug-reports.md |
| BUG-008 | BL-30 | LeaveCalculatorTest.durationWithinWorkWeek, durationSkipsWeekends | Major | bug-reports.md |
| BUG-009 | BL-50 | PayrollCalculatorTest.netIsGrossTimes077, netRoundsHalfUp | Major | bug-reports.md |
| BUG-010 | BL-60 | PayrollCalculatorTest.hourlyRateIsMonthlyDividedBy168 | Minor | bug-reports.md |
| BUG-011 | BL-80 | EmployeeValidatorTest.taxIdExactlyTenDigits, validateAllWrongReturnsAllCodes | Major | bug-reports.md |
| BUG-012 | BL-80 | EmployeeValidatorTest.minimumAgeSixteenOnHireDate | Major | bug-reports.md |

## 7. Висновок про стан модуля v1
Модуль v1 до випуску не готовий: 6 із 6 закладених дефектів у відпустках (BL-10, BL-30), нарахуваннях (BL-50, BL-60) і валідації даних працівника (BL-80, РНОКПП і вік) дають хибні дні відпустки, суми «на руки» і пропускають некоректні дані. Усі 13 червоних тестів лишаються в наборі для верифікації виправлень у v2 (спринт 5): closed або reopened. Відкриті питання замовнику: часткові дні BL-30 (крок 0,5) і BL-40 (duration ≥ 1).



