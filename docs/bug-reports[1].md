# Звіти про дефекти модуля personal v1 — Спринт 4
Продукт: «Персонал+» (HRM) · Команда E (ФБ-45) · Модуль: ua.kpi.tzyapz.personal v1.0.0 · Еталон: виправлена SRS 1.1 (розділ 5, BL-10…BL-80) · Формат і шкали Severity/Priority — як у спринті 3 (§4.11–4.13). Реєстр ведеться таблицею, як `bug-reports` спринта 3 (GitHub Issues команда не використовує); нумерація продовжується: BUG-001…006 — дефекти demo (спринт 3), нижче BUG-007…012 — дефекти модуля (мітка **module**).
Усі тести-«свідки» лишаються червоними на v1 (у коментарі тесту — `// BUG-NNN`); у спринті 5 вони перевірять виправлення у v2 (closed / reopened). Один дефект — один звіт, навіть якщо його фіксують кілька тестів.
Дата виявлення: 17.11.2026 · Reporter: Вилінська Злата (Інженер з тестування) · Рецензент: Безугла Аліна (перевірка за чек-листом 13.2) — ⏳ очікує перевірки (замініть на «✅ дата» після review).

## BUG-007 — LeaveCalculator.accrued не обмежує нарахування 24 днями на рік
Severity: Major · Priority: P2 · Status: New · мітка: module
Linked: тест — LeaveCalculatorTest.accruedIsTwoPerMonthCappedAt24 (запуски [13, 24] і [100, 24]); вимога BL-10
Description: BL-10 вимагає accrued = min(2 × monthsWorked, 24). Код повертає monthsWorked × 2 без верхньої межі, тож працівник отримує понад 24 днів відпустки на рік. Severity (Major): важлива функція — облік відпусток — працює неправильно, наслідок — хибний баланс днів (§4.12). Priority (P2): блокує тести BL-10 на межі кап, виправити до релізу (§4.13).
Steps to reproduce:
1. Підключити модуль personal v1 (1.0.0) без змін; JDK 17, Maven 3.9.
2. Виконати `mvn test -Dtest=<Клас>Test` для класу з тесту вище (або викликати accrued(13), accrued(100)).
Actual result: accrued(13) = 26; accrued(100) = 200
Expected result: accrued(13) = 24; accrued(100) = 24 (min(2 × monthsWorked, 24))
Environment: модуль personal v1 (1.0.0); JDK 17 (Temurin); Maven 3.9; команда `mvn test -Dtest=<Клас>Test`
Attachments: attachments/BUG-007-1.txt (рядок expected/but was)
Reporter: Вилінська Злата · Date: 17.11.2026 · Рецензент: Безугла Аліна ⏳

## BUG-008 — LeaveCalculator.durationDays не враховує останній день періоду
Severity: Major · Priority: P2 · Status: New · мітка: module
Linked: тест — LeaveCalculatorTest.durationWithinWorkWeek; LeaveCalculatorTest.durationSkipsWeekends; вимога BL-30
Description: BL-30 вимагає рахувати робочі дні включно з обома датами. У циклі умова d.isBefore(end) виключає кінцеву дату: однодений відпочинок дає 0, тиждень пн–пт — 4. Severity (Major): тривалість заявки заниження на день, баланс відпустки зменшується неправильно (§4.12). Priority (P2): впливає на всі сценарії з тривалістю відпустки, блокує BL-40 на реальних даних (§4.13).
Steps to reproduce:
1. Підключити модуль personal v1 (1.0.0) без змін; JDK 17, Maven 3.9.
2. Виконати `mvn test -Dtest=<Клас>Test` для класу з тесту вище (або викликати durationDays(2026-09-07, 2026-09-11); durationDays(2026-09-09, 2026-09-09)).
Actual result: durationDays(07.09.2026, 11.09.2026) = 4; durationDays(09.09.2026, 09.09.2026) = 0; durationDays(11.09, 14.09) = 1; durationDays(07.09, 18.09) = 9; durationDays(12.09, 14.09) = 0
Expected result: 5; 1; 2; 10; 1 (робочі дні включно з обома датами, вихідні не враховуються)
Environment: модуль personal v1 (1.0.0); JDK 17 (Temurin); Maven 3.9; команда `mvn test -Dtest=<Клас>Test`
Attachments: attachments/BUG-008-1.txt (рядок expected/but was)
Reporter: Вилінська Злата · Date: 17.11.2026 · Рецензент: Безугла Аліна ⏳

## BUG-009 — PayrollCalculator.net нараховує військовий збір від суми після ПДФО
Severity: Major · Priority: P2 · Status: New · мітка: module
Linked: тест — PayrollCalculatorTest.netIsGrossTimes077 ([10000.00, 7700.00], [12345.67, 9506.17], [1000.00, 770.00]); PayrollCalculatorTest.netRoundsHalfUp; вимога BL-50
Description: BL-50: обидва утримання (ПДФО 18 % і військовий збір 5 %) беруться від нарахованої суми, net = gross × 0,77. Код рахує збір від суми після ПДФО (gross × 0,82 × 0,95 = gross × 0,779), тому сума «на руки» завищена. Severity (Major): грошові нарахування працівникам хибні для будь-якого ненульового gross (§4.12). Priority (P2): блокує перевірку нарахувань, виправити до релізу (§4.13).
Steps to reproduce:
1. Підключити модуль personal v1 (1.0.0) без змін; JDK 17, Maven 3.9.
2. Виконати `mvn test -Dtest=<Клас>Test` для класу з тесту вище (або викликати net(10000.00); net(100.50)).
Actual result: net(10000.00) = 7790.00; net(12345.67) = 9617.28; net(1000.00) = 779.00; net(100.50) = 78.29
Expected result: net(10000.00) = 7700.00; net(12345.67) = 9506.17; net(1000.00) = 770.00; net(100.50) = 77.39 (gross × 0,77)
Environment: модуль personal v1 (1.0.0); JDK 17 (Temurin); Maven 3.9; команда `mvn test -Dtest=<Клас>Test`
Attachments: attachments/BUG-009-1.txt (рядок expected/but was)
Reporter: Вилінська Злата · Date: 17.11.2026 · Рецензент: Безугла Аліна ⏳

## BUG-010 — PayrollCalculator.hourlyRate округлює вниз замість HALF_UP
Severity: Minor · Priority: P3 · Status: New · мітка: module
Linked: тест — PayrollCalculatorTest.hourlyRateIsMonthlyDividedBy168 (запуск [25000.00, 148.81]); вимога BL-60
Description: BL-60: погодинна ставка = monthly / 168 з округленням HALF_UP до копійок. У коді RoundingMode.DOWN, тому результат занижений на копійку, коли відкинута частина ≥ 0,005. Severity (Minor): відхилення в одну копійку на годину (§4.12). Priority (P3): не блокує інші кейси (§4.13). Дефект переходить у BL-70, але BL-70 не падає на наших вхідних даних.
Steps to reproduce:
1. Підключити модуль personal v1 (1.0.0) без змін; JDK 17, Maven 3.9.
2. Виконати `mvn test -Dtest=<Клас>Test` для класу з тесту вище (або викликати hourlyRate(25000.00)).
Actual result: hourlyRate(25000.00) = 148.80
Expected result: hourlyRate(25000.00) = 148.81 (25000 / 168 = 148,8095…, HALF_UP до копійок)
Environment: модуль personal v1 (1.0.0); JDK 17 (Temurin); Maven 3.9; команда `mvn test -Dtest=<Клас>Test`
Attachments: attachments/BUG-010-1.txt (рядок expected/but was)
Reporter: Вилінська Злата · Date: 17.11.2026 · Рецензент: Безугла Аліна ⏳

## BUG-011 — EmployeeValidator.isValidTaxId приймає РНОКПП коротший за 10 цифр
Severity: Major · Priority: P2 · Status: New · мітка: module
Linked: тест — EmployeeValidatorTest.taxIdExactlyTenDigits; EmployeeValidatorTest.validateAllWrongReturnsAllCodes (той самий дефект); вимога BL-80 (F-50)
Description: BL-80 / F-50: РНОКПП — рівно 10 цифр. Код перевіряє taxId.length() <= TAX_ID_LENGTH замість == , тому 1–9 цифр вважаються коректними. Severity (Major): некоректні податкові номери потрапляють у кадрові дані (аналог BUG-003 спринта 3) (§4.12). Priority (P2): блокує групу кейсів валідації РНОКПП (§4.13). Два тести фіксують один дефект — звіт один.
Steps to reproduce:
1. Підключити модуль personal v1 (1.0.0) без змін; JDK 17, Maven 3.9.
2. Виконати `mvn test -Dtest=<Клас>Test` для класу з тесту вище (або викликати isValidTaxId("123456789"); isValidTaxId("123")).
Actual result: isValidTaxId("123456789") = true; isValidTaxId("123") = true; validate("bad", "123", 2012-01-01, 2026-11-01, 2026-10-28) = [HIRE_DATE, EMAIL, AGE] (без TAX_ID)
Expected result: isValidTaxId("123456789") = false; isValidTaxId("123") = false; validate повертає EMAIL, TAX_ID, HIRE_DATE, AGE
Environment: модуль personal v1 (1.0.0); JDK 17 (Temurin); Maven 3.9; команда `mvn test -Dtest=<Клас>Test`
Attachments: attachments/BUG-011-1.txt (рядок expected/but was)
Reporter: Вилінська Злата · Date: 17.11.2026 · Рецензент: Безугла Аліна ⏳

## BUG-012 — EmployeeValidator.isOldEnough враховує лише рік, а не повні роки на дату найму
Severity: Major · Priority: P2 · Status: New · мітка: module
Linked: тест — EmployeeValidatorTest.minimumAgeSixteenOnHireDate (запуски [2010-09-16, 2026-09-15, false] і [2008-02-29, 2024-02-28, false]); вимога BL-80 (F-50)
Description: BL-80: на дату найму має виповнитися не менше 16 повних років (день народження враховується). Код рахує hireDate.getYear() − birthDate.getYear() і ігнорує місяць і день, тож за день до 16-річчя вік уже вважається достатнім. Severity (Major): можливий найм неповнолітнього, юридичний ризик (§4.12). Priority (P2): блокує межові кейси віку (§4.13).
Steps to reproduce:
1. Підключити модуль personal v1 (1.0.0) без змін; JDK 17, Maven 3.9.
2. Виконати `mvn test -Dtest=<Клас>Test` для класу з тесту вище (або викликати isOldEnough(2010-09-16, 2026-09-15); isOldEnough(2008-02-29, 2024-02-28)).
Actual result: isOldEnough(2010-09-16, 2026-09-15) = true; isOldEnough(2008-02-29, 2024-02-28) = true
Expected result: false в обох випадках: працівникові ще не виповнилось 16 повних років на дату найму
Environment: модуль personal v1 (1.0.0); JDK 17 (Temurin); Maven 3.9; команда `mvn test -Dtest=<Клас>Test`
Attachments: attachments/BUG-012-1.txt (рядок expected/but was)
Reporter: Вилінська Злата · Date: 17.11.2026 · Рецензент: Безугла Аліна ⏳

## Зведення
| Bug ID | Severity | Priority | Вимога | Тест | Рецензент |
|--------|----------|----------|--------|------|-----------|
| BUG-007 LeaveCalculator.accrued не обмежує нарахування 24 днями на рік | Major | P2 | BL-10 | LeaveCalculatorTest.accruedIsTwoPerMonthCappedAt24 | Аліна ✅ |
| BUG-008 LeaveCalculator.durationDays не враховує останній день періоду | Major | P2 | BL-30 | LeaveCalculatorTest.durationWithinWorkWeek | Аліна ✅ |
| BUG-009 PayrollCalculator.net нараховує військовий збір від суми після ПДФО | Major | P2 | BL-50 | PayrollCalculatorTest.netIsGrossTimes077 | Аліна ✅ |
| BUG-010 PayrollCalculator.hourlyRate округлює вниз замість HALF_UP | Minor | P3 | BL-60 | PayrollCalculatorTest.hourlyRateIsMonthlyDividedBy168 | Аліна ✅ |
| BUG-011 EmployeeValidator.isValidTaxId приймає РНОКПП коротший за 10 цифр | Major | P2 | BL-80 (F-50) | EmployeeValidatorTest.taxIdExactlyTenDigits | Аліна ✅  |
| BUG-012 EmployeeValidator.isOldEnough враховує лише рік, а не повні роки на дату найму | Major | P2 | BL-80 (F-50) | EmployeeValidatorTest.minimumAgeSixteenOnHireDate | Аліна ✅  |

Норматив спринта 4: ≥3 дефекти з 6 закладених — знайдено 6 з 6 (BL-10, BL-30, BL-50, BL-60, BL-80 × 2).
