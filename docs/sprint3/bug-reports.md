# Звіти про дефекти — Спринт 3

> Продукт: «Персонал+» (HRM) · Команда E (ФБ-45) · Demo: OrangeHRM OS 5.9 (https://opensource-demo.orangehrmlive.com) · Еталон: виправлена SRS 1.1
> Артефакт **Інженера з тестування** (Безугла Аліна). Формат — за §4.11. **Реєстр дефектів ведеться цією таблицею** (§4.15 допускає таблицю `bug-reports.md` замість трекера з тими самими полями); окремого трекера GitHub Issues команда не використовує.
> **Виконано вживу на demo 21.10.2026**; скріншоти — реальні, у теці `attachments/`.
> Нормативи: дефектів **6** (≥6 для 3 осіб) ✅ · рівні severity: **Major, Normal, Minor** (≥3) ✅.
> Reporter (усі звіти): Безугла Аліна · Date: 21.10.2026 · Рецензент: Морквич Юлія (позначка — у колонці «Рецензент» зведення).

---

## BUG-001 — Пароль приймається без спеціального символу
- **Severity:** Major · **Priority:** P2 · **Status:** New
- **Linked:** TC-005; вимоги GUI-50, S-10
- **Description:** SRS 1.1 (GUI-50/S-10) вимагає пароль 8–64 символи з великою і малою латинськими літерами, цифрою і **спеціальним символом**. Demo під час створення користувача показує лише індикатор сили (Weak/Strong), але не блокує збереження пароля без спецсимволу чи без великої літери. **Обґрунтування Severity (Major):** важлива функція — захищеність облікових записів — працює не на належному рівні (§4.12), приймаються слабші паролі, ніж вимагає специфікація. **Priority (P2):** блокує коректні тест-кейси на правило пароля (S-10/GUI-50) і впливає на безпеку, тож виправити слід до релізу (§4.13).
- **Steps to reproduce:**
  1. Відкрити https://opensource-demo.orangehrmlive.com у Chrome, увійти як Admin / admin123.
  2. Admin → User Management → Add User; заповнити Role=ESS, Employee Name=Peter Mac Anderson, Status=Enabled, Username=teame.testpw01.
  3. У Password і Confirm Password ввести `Teampass1` (велика+мала+цифра, **без спецсимволу**).
  4. Натиснути Save.
- **Actual result:** Пароль позначено як «Weak», але користувача **збережено** — запис `teame.testpw01` з'явився у списку System Users.
- **Expected result:** За GUI-50/S-10 пароль без спеціального символу має відхилятися з повідомленням про невідповідність правилу; користувач не зберігається.
- **Environment:** demo 21.10.2026; OrangeHRM 5.9; Chrome 129.0; Windows 11; роль Admin.
- **Attachments:** `BUG-001-1.png` (список System Users із доданим `teame.testpw01` попри слабкий пароль).

---

## BUG-002 — Текст помилки входу не відповідає специфікації
- **Severity:** Minor · **Priority:** P3 · **Status:** New
- **Linked:** TC-002; вимога GUI-20
- **Description:** GUI-20 визначає повідомлення про невдалий вхід як «Wrong username or password». Demo показує інший текст — «Invalid credentials». **Обґрунтування Severity (Minor):** незначна втрата — проблема тексту інтерфейсу, сама функція входу працює коректно (§4.12). **Priority (P3):** не блокує тестування інших функцій, косметична розбіжність (§4.13).
- **Steps to reproduce:**
  1. Відкрити https://opensource-demo.orangehrmlive.com у Chrome (нова сесія).
  2. Username = `Admin`, Password = `wrongpass1`.
  3. Натиснути Login.
- **Actual result:** Над формою червоним показано **«Invalid credentials»**.
- **Expected result:** За GUI-20 — «Wrong username or password».
- **Environment:** demo 21.10.2026; OrangeHRM 5.9; Chrome 129.0; Windows 11; без входу.
- **Attachments:** `BUG-002-1.png` (повідомлення над формою входу).

---

## BUG-003 — Відсутнє поле і валідація РНОКПП
- **Severity:** Major · **Priority:** P2 · **Status:** New
- **Linked:** TC-007, TC-008; вимоги F-50, BL-80
- **Description:** F-50 вимагає перевірку формату РНОКПП (рівно 10 цифр, `isValidTaxId`) під час додавання працівника. На формі Add Employee і в профілі поля РНОКПП немає, тож валідацію виконати неможливо, а некоректний податковий номер не може бути відхилений. **Обґрунтування Severity (Major):** важлива перевірка даних працівника не працює — некоректні дані потрапляють у систему (§4.12). **Priority (P2):** блокує цілу групу кейсів на валідацію РНОКПП (TC-007, TC-008) і коректність кадрових даних (§4.13).
- **Steps to reproduce:**
  1. Відкрити https://opensource-demo.orangehrmlive.com у Chrome, увійти як Admin / admin123.
  2. PIM → Add Employee.
  3. Переглянути всі поля форми й вкладки профілю після збереження, шукаючи поле податкового номера (РНОКПП).
- **Actual result:** Форма містить лише Employee Full Name, Employee Id (авто), фото і перемикач Create Login Details. **Поля РНОКПП немає**; валідація 10 цифр не відбувається.
- **Expected result:** За F-50/BL-80 має бути поле РНОКПП із перевіркою формату (рівно 10 цифр) під час збереження.
- **Environment:** demo 21.10.2026; OrangeHRM 5.9; Chrome 129.0; Windows 11; роль HR через Admin.
- **Attachments:** `BUG-003-1.png` (форма Add Employee без поля РНОКПП, видно версію OrangeHRM OS 5.9).

---

## BUG-004 — Часткові дні дрібніші за крок 0,5 дня
- **Severity:** Minor · **Priority:** P3 · **Status:** New
- **Linked:** TC-019; вимоги F-70, GUI-70, BL-30
- **Description:** SRS 1.1 (F-70/BL-30) визначає часткові дні з кроком 0,5 дня. У формі Assign/Apply Leave для одного дня поле Duration має варіант «Specify Time», що відкриває поля часу з точністю до хвилин — гранулярність, дрібніша за 0,5 дня. **Обґрунтування Severity (Minor):** незначна втрата — крайній варіант часткових днів, основний сценарій (повний/пів дня) працює (§4.12). **Priority (P3):** не блокує основні кейси відпусток (§4.13).
- **Steps to reproduce:**
  1. Відкрити https://opensource-demo.orangehrmlive.com у Chrome, увійти як Admin / admin123.
  2. Leave → Assign Leave: Employee = Amelia Brown (з лімітом CAN - Vacation), Leave Type = CAN - Vacation, From = 2026-23-10, To = 2026-23-10 (один день).
  3. У полі Duration обрати `Specify Time`.
  4. Переглянути поля From/To з часом.
- **Actual result:** Duration = «Specify Time» відкриває поля часу From/To (за замовчуванням 09:00 AM – 05:00 PM) з вибором часу до хвилин — тривалість не кратна 0,5 дня.
- **Expected result:** За F-70/BL-30 часткові дні обмежені кроком 0,5 дня; дрібніша гранулярність не допускається.
- **Environment:** demo 21.10.2026; OrangeHRM 5.9; Chrome 129.0; Windows 11; роль через Admin.
- **Attachments:** `BUG-004-1.png` (Leave → Assign Leave, Duration = «Specify Time», поля часу From/To).

---

## BUG-005 — Немає нагадування про заявку, що очікує погодження понад 24 години
- **Severity:** Normal · **Priority:** P3 · **Status:** New
- **Linked:** TC-014; вимога F-90
- **Description:** F-90 (виправлена SRS 1.1) передбачає, що керівник отримує нагадування в кабінеті системи про заявку, яка очікує погодження понад 24 години. У demo такого нагадування немає ні в модулі Leave, ні в блоці Dashboard → «My Actions». **Обґрунтування Severity (Normal):** користуватись можна, але є незручність — керівник може пропустити заявку без нагадування (§4.12 Normal). **Priority (P3):** не блокує виконання інших кейсів (§4.13).
- **Steps to reproduce:**
  1. Відкрити https://opensource-demo.orangehrmlive.com у Chrome, увійти як Admin / admin123.
  2. Переконатися, що для John Doe є ліміт CAN - Vacation (Leave → Entitlements).
  3. Leave → Apply: Leave Type = CAN - Vacation, From = 2026-29-10, To = 2026-29-10 → Apply. Заявка отримує статус **Pending Approval** — умова F-90 (заявка очікує погодження) виконана.
  4. Відкрити Dashboard → блок «My Actions», а також Leave → Leave List.
  5. Шукати нагадування/сповіщення керівнику про заявку, що очікує погодження понад 24 години.
- **Actual result:** Блок «My Actions» показує лише «Pending Self Review» і «Candidate to Interview»; у Leave List заявка присутня у статусі Pending Approval, але жодного нагадування про перевищення 24 год немає.
- **Expected result:** За F-90 — нагадування в кабінеті системи про заявку, що очікує погодження понад 24 год.
- **Environment:** demo 21.10.2026; OrangeHRM 5.9; Chrome 129.0; Windows 11; роль керівника через Admin.
- **Attachments:** `BUG-005-1.png` (Dashboard → My Actions без нагадування про відпустку).

---

## BUG-006 — Активна вакансія публікується на публічній сторінці й у RSS
- **Severity:** Normal · **Priority:** P2 · **Status:** New
- **Linked:** TC-017; вимога F-130
- **Description:** F-130 (виправлена SRS 1.1) визначає, що активна вакансія публікується на **внутрішній** сторінці вакансій продукту; зовнішні ресурси — поза межами v1.0. У demo активна вакансія з увімкненим «Publish in RSS Feed and Web Page» публікується на **публічній сторінці** `…/recruitmentApply/jobs.html` і в RSS, доступних **без входу**. **Обґрунтування Severity (Normal):** функція працює, але не на належному рівні — дані виходять ширше, ніж дозволено специфікацією (§4.12). **Priority (P2):** впливає на відповідність вимозі і приватність публікації вакансій, тож варто виправити до релізу (§4.13).
- **Steps to reproduce:**
  1. Відкрити https://opensource-demo.orangehrmlive.com у Chrome, увійти як Admin / admin123.
  2. Recruitment → Vacancies → Add — видно перемикач «Publish in RSS Feed and Web Page» з публічними URL (`jobs.html`, `jobs.rss`). Створити активну вакансію (Active).
  3. Відкрити `https://opensource-demo.orangehrmlive.com/web/index.php/recruitmentApply/jobs.html` у новій сесії без входу.
- **Actual result:** Публічна сторінка без входу показує список усіх активних вакансій (test, Senior Support Specialist, Senior QA Lead, Junior Account Assistant, Payroll Administrator, Sales Representative, Software Engineer) з кнопками «Apply».
- **Expected result:** За F-130 публікація обмежена внутрішньою сторінкою вакансій; зовнішнє/публічне розміщення — поза межами v1.0.
- **Environment:** demo 21.10.2026; OrangeHRM 5.9; Chrome 129.0; Windows 11.
- **Attachments:** `BUG-006-1.png` (публічна сторінка вакансій `jobs.html` без входу).

---

## Зведення
| Bug ID | Severity | Priority | Вимога | Кейс | Рецензент |
|---|---|---|---|---|---|
| BUG-001 Пароль без спецсимволу | Major | P2 | GUI-50, S-10 | TC-005 | Юлія ✅ 21.10 |
| BUG-002 Текст помилки входу | Minor | P3 | GUI-20 | TC-002 | Юлія ✅ 21.10 |
| BUG-003 Відсутнє поле РНОКПП | Major | P2 | F-50, BL-80 | TC-007, TC-008 | Юлія ✅ 21.10 |
| BUG-004 Часткові дні < 0,5 дня | Minor | P3 | F-70, BL-30 | TC-019 | Юлія ✅ 21.10 |
| BUG-005 Немає нагадування >24 год | Normal | P3 | F-90 | TC-014 | Юлія ✅ 21.10 |
| BUG-006 Публічна сторінка вакансій | Normal | P2 | F-130 | TC-017 | Юлія ✅ 21.10 |
