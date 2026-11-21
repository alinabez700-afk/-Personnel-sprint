# Протокол запуску (Surefire) — модуль personal v1

Дата: 19.11.2026 · Команда E · Windows 11, JDK 17.0.21 (Temurin), Maven 3.9.9 · Тест-класи: LeaveCalculatorTest, PayrollCalculatorTest, EmployeeValidatorTest, LoginUiTest, NavigationUiTest.
Результати реального прогону на модулі v1 (без змін).

- Команда 1 (юніт): `mvn clean test` → `Tests run: 72, Failures: 13, Errors: 0, Skipped: 0` → BUILD FAILURE (13 червоних тестів — закладені дефекти v1), час 53,5 с
- Команда 2 (усе): `mvn test "-DexcludedGroups=none"` → `Tests run: 80, Failures: 13, Errors: 0, Skipped: 0` (72 юніт + 8 UI; усі 8 UI-тестів пройшли, червоні тільки ті самі 13 юніт-запусків), час 1 хв 53 с
- Звіти: `docs/sprint4/attachments/ua.kpi.tzyapz.personal.*.txt`
## Червоні тести і дефекти (кожен червоний тест = звіт BUG-NNN)
| Тест (клас.метод[запуск]) | Вимога | Expected / but was | BUG |
|---|---|---|---|
| LeaveCalculatorTest.accruedIsTwoPerMonthCappedAt24[13, 24] | BL-10 | expected: <24> but was: <26> | BUG-007 |
| LeaveCalculatorTest.accruedIsTwoPerMonthCappedAt24[100, 24] | BL-10 | expected: <24> but was: <200> | BUG-007 |
| LeaveCalculatorTest.durationWithinWorkWeek | BL-30 | expected: <5> but was: <4>; expected: <1> but was: <0> | BUG-008 |
| LeaveCalculatorTest.durationSkipsWeekends | BL-30 | expected: <2> but was: <1>; <10> / <9>; <1> / <0> | BUG-008 |
| PayrollCalculatorTest.netIsGrossTimes077[10000.00, 7700.00] | BL-50 | очікувалось 7700.00, отримано 7790.00 | BUG-009 |
| PayrollCalculatorTest.netIsGrossTimes077[12345.67, 9506.17] | BL-50 | очікувалось 9506.17, отримано 9617.28 | BUG-009 |
| PayrollCalculatorTest.netIsGrossTimes077[1000.00, 770.00] | BL-50 | очікувалось 770.00, отримано 779.00 | BUG-009 |
| PayrollCalculatorTest.netRoundsHalfUp | BL-50 | очікувалось 77.39, отримано 78.29 | BUG-009 |
| PayrollCalculatorTest.hourlyRateIsMonthlyDividedBy168[25000.00, 148.81] | BL-60 | очікувалось 148.81, отримано 148.80 | BUG-010 |
| EmployeeValidatorTest.taxIdExactlyTenDigits | BL-80 | isValidTaxId("123456789") = true, очікувалось false | BUG-011 |
| EmployeeValidatorTest.validateAllWrongReturnsAllCodes | BL-80 | expected 4 кодів, отримано 3 (немає TAX_ID) | BUG-011 |
| EmployeeValidatorTest.minimumAgeSixteenOnHireDate[2010-09-16, 2026-09-15, false] | BL-80 | expected: <false> but was: <true> | BUG-012 |
| EmployeeValidatorTest.minimumAgeSixteenOnHireDate[2008-02-29, 2024-02-28, false] | BL-80 | expected: <false> but was: <true> | BUG-012 |

13 червоних запусків → 6 дефектів (BUG-007…012). Решта 59 запусків зелені. Червоні тести лишаються в наборі для верифікації на v2 (спринт 5).


Повторні запуски UI (mvn test -DexcludedGroups=none): 2 з 2, результат однаковий: Tests run: 80, Failures: 13.
