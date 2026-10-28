# Матриця покриття BL → правило → тести (Персонал+, модуль v1)

Джерело очікуваних значень — розділ 5 виправленої SRS (BL-). Якщо у вашій виправленій SRS змінились формулювання, звірте стовпець «Правило».
Рецензент — за схемою перехресного review команди з 3 осіб (КТ виконує і роль СМ).

| BL | Метод | Правило | Тести (клас.метод) | Межі / помилки | Автор | Рецензент |
|----|-------|---------|--------------------|----------------|-------|-----------|
| BL-10 | LeaveCalculator.accrued | min(2×міс, 24) | LeaveCalculatorTest.accruedIsTwoPerMonthCappedAt24 (6 запусків); accruedNegativeMonthsThrows | 0, 12, 13, 100; −1 → IAE | Морквич Ю. | Вилінська З. |
| BL-20 | LeaveCalculator.carryOver | min(unused, 5) | carryOverIsCappedAtFive (6); carryOverNegativeThrows | 4, 5, 6; −1 → IAE | Морквич Ю. | Вилінська З. |
| BL-30 | LeaveCalculator.durationDays | робочі дні включно, без сб/нд | durationWithinWorkWeek; durationSkipsWeekends; durationWeekendOnlyIsZero; durationInvalidInputThrows | пн–пт=5, пт–пн=2, сб–пн=1, сб–нд=0; start>end, null → IAE | Морквич Ю. | Вилінська З. |
| BL-40 | LeaveCalculator.canRequest | 1 ≤ duration ≤ balance | canRequestBoundaries (7) | =баланс, баланс+1, 0 | Морквич Ю. | Вилінська З. |
| BL-50 | PayrollCalculator.net | gross×0,77, HALF_UP | netIsGrossTimes077 (4); netRoundsHalfUp; netOfOneKopiyka; netInvalidInputThrows | 0, 0.01, 100.50 (77,385); −0.01, null → IAE | Вилінська З. | Безугла А. |
| BL-60 | PayrollCalculator.hourlyRate | monthly/168, HALF_UP | hourlyRateIsMonthlyDividedBy168 (4); hourlyRateInvalidInputThrows | 0, 10000, 25000; −1, null → IAE | Вилінська З. | Безугла А. |
| BL-70 | PayrollCalculator.overtimePay | округлена ставка×1,5×год | overtimeUsesRoundedHourlyRate (4); overtimeInvalidInputThrows | 0 год, 0,5 год, 2 год (178.56); −0,5 год, −оклад → IAE | Вилінська З. | Безугла А. |
| BL-80 | EmployeeValidator.isValidEmail | формат локальна@домен.зона | validEmailsAccepted (4); invalidEmailsRejected (9) | зона 1 літера, без @, пробіл, `_` у домені | Безугла А. | Морквич Ю. |
| BL-80 | isValidTaxId | рівно 10 цифр | taxIdExactlyTenDigits; taxIdWithOtherSymbolsRejected (5) | 9, 10, 11 цифр, літери, пробіл | Безугла А. | Морквич Ю. |
| BL-80 | isHireDateValid | найм ≤ today | hireDateNotInFuture | вчора, сьогодні, завтра | Безугла А. | Морквич Ю. |
| BL-80 | isOldEnough | ≥16 повних років | minimumAgeSixteenOnHireDate (6) | день 16-річчя, ±1 день, 29.02 | Безугла А. | Морквич Ю. |
| BL-80 | validate | список кодів EMAIL/TAX_ID/HIRE_DATE/AGE | validateAllCorrectReturnsEmpty; validateAllWrongReturnsAllCodes; validateOnlyEmailWrong; validateMissingDateThrows | порожній список; 4 коди; null-дати → IAE | Безугла А. | Морквич Ю. |

**Підсумок:** 27 тестових методів (≥12 за нормативом для 3 осіб); 9 + 8 + 10 = 27 → Юлія 9, Злата 8, Аліна 10 (кожен ≥3).

## Зауваження до SRS v1.0, виявлені під час складання матриці
(перевірте, чи виправлено у вашій SRS зі спринта 2; тести написано за BL-розділом)
- BL-50: речення «Розрахунок має працювати без помилок для будь-яких вхідних даних» суперечить вимозі кидати IAE для gross < 0 — тест перевіряє IAE (конкретна вимога переважає).
- F-70: «календарних днів», тоді як BL-30 — робочі дні; для модуля чинна BL-30.
- GUI-20 «Wrong username or password» ≠ F-20 «Invalid credentials»; UI-тест TC-02 перевіряє «Invalid credentials» (фактична поведінка demo, узгоджена з F-20).
