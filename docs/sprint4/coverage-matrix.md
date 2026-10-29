# Матриця покриття BL → правило → тести (Персонал+, модуль v1) — спринт 4

Еталон: SRS 1.1 (виправлена, спринт 2), розділ 5. Рецензування перехресне: Юлію — Злата, Злату — Аліна, Аліну — Юлія (позначки — у колонці «Рецензент»).

| BL | Метод | Правило (SRS 1.1) | Тести | Межі / помилки | Пов'язані F / TC | Автор | Рецензент |
|----|-------|-------------------|-------|----------------|------------------|-------|-----------|
| BL-10 | LeaveCalculator.accrued | min(2×міс, 24) | LeaveCalculatorTest.accruedIsTwoPerMonthCappedAt24 (6); accruedNegativeMonthsThrows | 0, 12, 13, 100; −1 → IAE | F-110 | Морквич Ю. | Вилінська З. |
| BL-20 | LeaveCalculator.carryOver | min(unused, 5) | carryOverIsCappedAtFive (6); carryOverNegativeThrows | 4, 5, 6; −1 → IAE | F-110 | Морквич Ю. | Вилінська З. |
| BL-30 | LeaveCalculator.durationDays | робочі дні включно, без сб/нд | durationWithinWorkWeek; durationSkipsWeekends; durationWeekendOnlyIsZero; durationInvalidInputThrows | пн–пт=5, пт–пн=2, сб–пн=1, сб–нд=0; start>end, null → IAE | F-70, TC-012 | Морквич Ю. | Вилінська З. |
| BL-40 | LeaveCalculator.canRequest | 1 ≤ duration ≤ balance | canRequestBoundaries (7) | =баланс, баланс+1, 0 | F-70, GUI-70, TC-010, TC-011 | Морквич Ю. | Вилінська З. |
| BL-50 | PayrollCalculator.net | gross×0,77, HALF_UP | netIsGrossTimes077 (4); netRoundsHalfUp; netOfOneKopiyka; netInvalidInputThrows | 0, 0.01, 100.50; −0.01, null → IAE | — | Вилінська З. | Безугла А. |
| BL-60 | PayrollCalculator.hourlyRate | monthly/168, HALF_UP | hourlyRateIsMonthlyDividedBy168 (4); hourlyRateInvalidInputThrows | 0, 10000, 25000; −1, null → IAE | — | Вилінська З. | Безугла А. |
| BL-70 | PayrollCalculator.overtimePay | округлена ставка×1,5×год | overtimeUsesRoundedHourlyRate (4); overtimeInvalidInputThrows | 0, 0,5, 2 год; від'ємні → IAE | — | Вилінська З. | Безугла А. |
| BL-80 | EmployeeValidator.isValidEmail | локальна@домен.зона | validEmailsAccepted (4); invalidEmailsRejected (9) | без @, без зони, зона 1 літера, пробіл, `_` у домені | F-140, TC-018 | Безугла А. | Морквич Ю. |
| BL-80 | isValidTaxId | рівно 10 цифр | taxIdExactlyTenDigits; taxIdWithOtherSymbolsRejected (5) | 9/10/11 цифр, літери, пробіл | F-50, TC-007, TC-008 | Безугла А. | Морквич Ю. |
| BL-80 | isHireDateValid | найм ≤ today | hireDateNotInFuture | вчора, сьогодні, завтра | F-50 | Безугла А. | Морквич Ю. |
| BL-80 | isOldEnough | ≥16 повних років | minimumAgeSixteenOnHireDate (6) | день 16-річчя, ±1 день, 29.02 | F-50 | Безугла А. | Морквич Ю. |
| BL-80 | validate | коди EMAIL/TAX_ID/HIRE_DATE/AGE | validateAllCorrectReturnsEmpty; validateAllWrongReturnsAllCodes; validateOnlyEmailWrong; validateMissingDateThrows | порожній список; 4 коди; null → IAE | F-50 | Безугла А. | Морквич Ю. |

**Підсумок:** 27 методів (норматив ≥12): Юлія 9, Злата 8, Аліна 10.

## Примітки щодо SRS 1.1
1. **BL-30, часткові дні (дефект №15 спринта 2):** правило «0,5 за позначений південь» позначено ⚠ як таке, що потребує підтвердження замовником, і сигнатура методу для часткових днів у SRS не наведена — юніт-тестів на нього немає (відкрите питання для демо).
2. **BL-40 і часткові дні:** BL-40 лишається «duration ≥ 1», тож заявка на 0,5 дня за формулою false. Тест перевіряє BL-40 дослівно; це спостереження для замовника.
3. **BL-80:** текст SRS 1.1 коротший за v1.0 (немає переліку допустимих символів email, «сьогодні дозволено», «день народження враховується»). Тести використовують уточнення з v1.0. Якщо замовник вважає чинним лише скорочений текст, приберіть три кейси email (зона з 1 літери, `_` у домені, зона з цифр) і залиште решту.
4. **F-70/F-110:** Pending «резервує», а не віднімає — це правило рівня UI/продукту, у модулі v1 його методів немає, юніт-тестів немає.
