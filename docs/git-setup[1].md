# Спринт 4 у репозиторії https://github.com/alinabez700-afk/-Personnel-sprint

Структура репозиторію: `docs/sprint2`, `docs/sprint3`, `docs/team`. Спринт 4 додається так само: **`docs/sprint4/`** (документи, `attachments/`), а Maven-проєкт — у корені (`pom.xml`, `src/`, `selenium-ide/`). Дефекти ведуться таблицею (як у sprint3), GitHub Issues не потрібні.

## Заливка через браузер (Add file → Upload files)
1. Розпакуйте архів. Модуль v1 (без змін), `pom.xml` (з пакета викладача) і `.gitignore` (зі стартового проєкту) уже лежать в архіві. Перевірте `mvn clean test` локально: очікується Tests run: 72, Failures: 13.
2. Кожна заливає свої файли зі свого акаунта (окремий коміт, повідомлення на кшталт `[BL-30] тести LeaveCalculator`):
   - **Юлія:** `src/test/.../LeaveCalculatorTest.java`, `docs/sprint4/coverage-matrix.md`, `docs/sprint4/ui-scenarios.md`
   - **Злата:** `pom.xml`, `src/main/...` (модуль v1), `src/test/.../PayrollCalculatorTest.java`, `src/test/.../ui/*`, `selenium-ide/*`, `docs/sprint4/surefire-summary.md`
   - **Аліна:** `src/test/.../EmployeeValidatorTest.java`, `README.md` (якщо в корені вже є — злити), `.gitignore`, `docs/sprint4/automation.md`, `tasks.md`, `retro.md`, `review-checklist.md`, `bug-reports.md`
3. Після запуску `mvn clean test`: уривки звіту Surefire → `docs/sprint4/attachments/`, заповнити `surefire-summary.md`, `bug-reports.md` (BUG-007…), `automation.md`, перехресні позначки «✅ дата».
4. Тег (за бажанням): `v1-sprint4`.
