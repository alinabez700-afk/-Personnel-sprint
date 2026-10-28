# UI-сценарії WebDriver (demo: OrangeHRM, Персонал+)

Вхід: Admin / admin123 (звірте на сторінці входу demo). Тайм-аут очікувань 20 с. Номери TC узяті умовно — замініть на ID з вашого журналу спринта 3, якщо вони інші (і в `@DisplayName`).

| TC | Вимога | Кроки | Локатори | Очікування (явні) | Метод |
|----|--------|-------|----------|-------------------|-------|
| TC-01 | F-10 | відкрити /auth/login, ввести Admin/admin123, Login | `input[name='username']`, `input[name='password']`, `button[type='submit']` | urlContains /dashboard/index | LoginUiTest.validLoginOpensDashboard |
| TC-02 | F-20 | ввести Admin / хибний пароль | `.oxd-alert-content-text` | visibilityOfElementLocated | LoginUiTest.wrongPasswordShowsInvalidCredentials |
| TC-03 | F-20, GUI-40 | натиснути Login з порожніми полями | `.oxd-input-field-error-message` | лямбда: ≥2 повідомлень | LoginUiTest.emptyFieldsShowRequired |
| TC-04 | GUI-30 | вхід → меню користувача → Logout | `.oxd-userdropdown-tab`, `a[href$='/auth/logout']` | elementToBeClickable, urlContains /auth/login | LoginUiTest.logoutReturnsToLogin |
| TC-05 | F-40 | вхід → PIM | `a[href$='/pim/viewPimModule']`, relative XPath «Records Found» | urlContains /pim/viewEmployeeList | NavigationUiTest.pimOpensEmployeeList |
| TC-06 | F-90 | вхід → Leave | `a[href$='/leave/viewLeaveModule']`, label «Show Leave with Status» | urlContains /leave/viewLeaveList | NavigationUiTest.leaveOpensLeaveList |

Жорсткі перевірки — передумови (вхід); м'які (`assertAll`) — кілька властивостей одного результату. Абсолютних XPath і `Thread.sleep` немає.
Ланцюжок «ліміт → заявка → погодження» не автоматизовано: на спільному demo він залежить від чужих даних і лишається ручним.
