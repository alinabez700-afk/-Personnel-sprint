# Звіти про дефекти — Спринт 3

> Продукт: «Персонал+» (HRM) · Команда E (ФБ-45) · Demo: OrangeHRM OS 5.9 (https://opensource-demo.orangehrmlive.com) · Еталон: виправлена SRS 1.1
> Артефакт **Інженера з тестування** (Безугла Аліна). Формат — за §4.11. Трекер: GitHub Issues (мітки `status:*`, `severity:*`, `priority:*`, `sprint:3`); тут — дублікат-таблиця з тими самими полями.
> **Виконано вживу на demo 21.10.2026**; скріншоти — реальні, у теці `attachments/`.
> Нормативи: дефектів **6** (≥6 для 3 осіб) ✅ · рівні severity: **Major, Normal, Minor** (≥3) ✅ · кожен прив'язаний до failed-кейсу і вимоги.
> Reporter (усі звіти): Безугла Аліна · Date: 21.10.2026 · Рецензент: Морквич Юлія (позначка — у колонці «Рецензент» зведення).

---

## BUG-001 — Пароль приймається без спеціального символу
- **Severity:** Major · **Priority:** P2 · **Status:** New
- **Linked:** TC-005; вимоги GUI-50, S-10
- **Description:** SRS 1.1 (GUI-50/S-10) вимагає пароль 8–64 символи з великою і малою латинськими літерами, цифрою і **спеціальним символом**. Demo під час створення користувача показує лише індикатор сили пароля (Weak/Strong), але не блокує збереження пароля без спецсимволу.
- **Steps to reproduce:**
  1. Увійти як Admin. Admin → User Management → Add User.
  2. User Role = ESS; Employee Name = Peter Mac Anderson; Status = Enabled; Username = `teamE.testpw01`.
  3. Password і Confirm Password = `Testuser1` (велика+мала+цифра, **без спецсимволу**).
  4. Натиснути Save.
- **Actual result:** Пароль позначено як «Weak», але користувача **збережено** — запис `teamE.testpw01` з'явився у списку System Users (6 Records Found).
- **Expected result:** За GUI-50/S-10 пароль без спеціального символу має відхилятися; користувач не зберігається.
- **Environment:** demo 21.10.2026; OrangeHRM 5.9; Chrome; Windows 11; роль Admin.
- **Attachments:** `BUG-001-1.png` (створеного користувача додано попри слабкий пароль).

---

## BUG-002 — Текст помилки входу не відповідає специфікації
- **Severity:** Minor · **Priority:** P3 · **Status:** New
- **Linked:** TC-002; вимога GUI-20
- **Description:** GUI-20 визначає повідомлення про невдалий вхід як «Wrong username or password». Demo показує інший текст — «Invalid credentials».
- **Steps to reproduce:**
  1. Відкрити demo у Chrome (нова сесія).
  2. Username = `Admin`, Password = `wrongpass1`.
  3. Натиснути Login.
- **Actual result:** Над формою червоним показано **«Invalid credentials»**.
- **Expected result:** За GUI-20 — «Wrong username or password».
- **Environment:** demo 21.10.2026; OrangeHRM 5.9; Chrome; Windows 11; без входу.
- **Attachments:** `BUG-002-1.png` (повідомлення над формою входу).

---

## BUG-003 — Відсутнє поле і валідація РНОКПП
- **Severity:** Major · **Priority:** P2 · **Status:** New
- **Linked:** TC-007, TC-008; вимоги F-50, BL-80
- **Description:** F-50 вимагає перевірку формату РНОКПП (рівно 10 цифр, `isValidTaxId`) під час додавання працівника. На формі Add Employee поле РНОКПП відсутнє, тож валідацію виконати неможливо.
- **Steps to reproduce:**
  1. Увійти як Admin. PIM → Add Employee.
  2. Переглянути всі поля форми.
- **Actual result:** Форма містить лише Employee Full Name, Employee Id (авто `0386`), фото і перемикач Create Login Details. **Поля РНОКПП немає**; валідація 10 цифр не відбувається.
- **Expected result:** За F-50/BL-80 має бути поле РНОКПП із перевіркою формату (рівно 10 цифр) під час збереження.
- **Environment:** demo 21.10.2026; OrangeHRM 5.9; Chrome; Windows 11; роль HR через Admin.
- **Attachments:** `BUG-003-1.png` (форма Add Employee без поля РНОКПП).

---

## BUG-004 — Часткові дні дрібніші за крок 0,5 дня
- **Severity:** Minor · **Priority:** P3 · **Status:** New
- **Linked:** TC-019; вимоги F-70, GUI-70, BL-30
- **Description:** SRS 1.1 (F-70/BL-30) визначає часткові дні з кроком 0,5 дня. У формі заявки/призначення відпустки варіант Partial Days → «Specify Time» дозволяє ввести довільний проміжок у годинах, тобто гранулярність, дрібнішу за 0,5 дня.
- **Steps to reproduce:**
  1. Увійти як Admin. Leave → Assign Leave; обрати працівника з лімітом, Leave Type, From Date = To Date (один день).
  2. У полі Partial Days обрати `Specify Time`.
  3. Ввести проміжок у годинах і переглянути обчислену тривалість.
- **Actual result:** Для одного дня поле Duration має варіант «Specify Time», що відкриває поля From/To з годинами (за замовчуванням 09:00 AM – 05:00 PM) і вибором часу з точністю до хвилин — гранулярність дрібніша за крок 0,5 дня.
- **Expected result:** За F-70/BL-30 часткові дні обмежені кроком 0,5 дня.
- **Environment:** demo 21.10.2026; OrangeHRM 5.9; Chrome; Windows 11; роль через Admin (працівнику Amelia Brown додано ліміт CAN - Vacation).
- **Attachments:** `BUG-004-1.png` (Duration = «Specify Time», поля часу From/To).

---

## BUG-005 — Немає нагадування про заявку, що очікує погодження понад 24 години
- **Severity:** Normal · **Priority:** P3 · **Status:** New
- **Linked:** TC-014; вимога F-90
- **Description:** F-90 (виправлена SRS 1.1) передбачає нагадування в кабінеті системи про заявку, що очікує погодження понад 24 години. У demo такого нагадування немає ні в модулі Leave, ні в блоці Dashboard → «My Actions».
- **Steps to reproduce:**
  1. Увійти як Admin. Відкрити Dashboard → блок «My Actions».
  2. Перевірити наявність нагадування про заявки на відпустку, що очікують погодження.
- **Actual result:** Блок «My Actions» показує лише «(1) Pending Self Review» і «(1) Candidate to Interview»; нагадування про заявку на відпустку, що очікує >24 год, немає.
- **Expected result:** За F-90 — нагадування в кабінеті системи про заявку, що очікує погодження понад 24 год.
- **Environment:** demo 21.10.2026; OrangeHRM 5.9; Chrome; Windows 11; роль керівника через Admin.
- **Attachments:** `BUG-005-1.png` (Dashboard → My Actions без нагадування про відпустку).

---

## BUG-006 — Активна вакансія публікується на публічній сторінці й у RSS
- **Severity:** Normal · **Priority:** P2 · **Status:** New
- **Linked:** TC-017; вимога F-130
- **Description:** F-130 (виправлена SRS 1.1) визначає, що активна вакансія публікується на **внутрішній** сторінці вакансій продукту; зовнішні портали — поза межами v1.0. У demo активна вакансія з увімкненим «Publish in RSS Feed and Web Page» публікується на **публічній сторінці** `…/recruitmentApply/jobs.html` і в RSS, доступних **без входу**, тобто ширше, ніж дозволяє SRS 1.1.
- **Steps to reproduce:**
  1. Увійти як Admin. Recruitment → Vacancies → Add — видно перемикач «Publish in RSS Feed and Web Page» з публічними URL (`jobs.html`, `jobs.rss`).
  2. Відкрити `https://opensource-demo.orangehrmlive.com/web/index.php/recruitmentApply/jobs.html` у новій сесії без входу.
- **Actual result:** Публічна сторінка без входу показує список усіх активних вакансій (test, Senior Support Specialist, Senior QA Lead, Junior Account Assistant, Payroll Administrator, Sales Representative, Software Engineer) з кнопками «Apply».
- **Expected result:** За F-130 публікація обмежена внутрішньою сторінкою вакансій; зовнішнє/публічне розміщення — поза межами v1.0.
- **Environment:** demo 21.10.2026; OrangeHRM 5.9; Chrome; Windows 11.
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
