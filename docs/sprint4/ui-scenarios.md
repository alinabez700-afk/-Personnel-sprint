# UI-сценарії WebDriver (demo: OrangeHRM OS 5.9, Персонал+) — спринт 4

Еталон — SRS 1.1; вхід Admin / admin123. Тайм-аут очікувань 20 с. Усі TC — з журналу тест-кейсів спринта 3 (TC-001…TC-020).
Не автоматизовано (залежать від спільних даних demo й кількох ролей, лишаються ручними): TC-004/005 (створення користувача, пароль), TC-007/008 (РНОКПП — поля на demo немає, BUG-003), TC-010…TC-016 (заявки, ліміти, погодження), TC-017/018 (вакансії, кандидати), TC-019 (часткові дні, BUG-004).

| TC | Вимоги SRS 1.1 | Кроки | Локатори | Явні очікування | Метод |
|----|----------------|-------|----------|-----------------|-------|
| TC-001 | F-10, GUI-10 | відкрити /auth/login; перевірити елементи | `.orangehrm-login-title`, `input[name='username']`, `input[name='password']`, `.orangehrm-login-forgot-header` | visibilityOfElementLocated | LoginUiTest.loginPageHasElementsPerGui10 |
| TC-001 | F-10 | ввести Admin/admin123, Login | `input[name='username']`, `input[name='password']`, `button[type='submit']` | urlContains /dashboard/index | LoginUiTest.validLoginOpensDashboard |
| TC-002 | F-20 (GUI-20, BUG-002) | Admin / хибний пароль | `.oxd-alert-content-text` | visibilityOfElementLocated | LoginUiTest.wrongPasswordShowsInvalidCredentials |
| TC-003 | F-20, GUI-40 | Login з порожніми полями | `.oxd-input-field-error-message` | лямбда: ≥2 повідомлень | LoginUiTest.emptyFieldsShowRequired |
| TC-020 | GUI-30 | вхід → меню користувача → Logout | `.oxd-userdropdown-tab`, `a[href$='/auth/logout']` | elementToBeClickable, urlContains /auth/login | LoginUiTest.logoutReturnsToLogin |
| TC-020 | GUI-30 | вхід → бічне меню | `.oxd-main-menu-item--name` | лямбда: ≥12 пунктів | NavigationUiTest.sidebarContainsModules |
| TC-006 | F-40, GUI-60 | вхід → PIM | `a[href$='/pim/viewPimModule']`, XPath «Records Found» | urlContains /pim/viewEmployeeList | NavigationUiTest.pimOpensEmployeeList |
| TC-014 | F-90 (крок 1) | вхід → Leave | `a[href$='/leave/viewLeaveModule']`, label «Show Leave with Status» | urlContains /leave/viewLeaveList | NavigationUiTest.leaveOpensLeaveList |

8 методів (норматив ≥4). Жорсткі перевірки — передумови (вхід), м'які (`assertAll`) — кілька властивостей одного результату.
Розбіжність GUI-20 / F-20 в самій SRS 1.1 («Wrong username or password» проти «Invalid credentials») задокументована як BUG-002; TC-002 в автотесті перевіряє текст F-20.
