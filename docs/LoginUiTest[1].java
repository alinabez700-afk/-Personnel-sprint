package ua.kpi.tzyapz.personal.ui;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * UI-сценарії входу/виходу за SRS 1.1 і тест-кейсами спринта 3: TC-001, TC-002, TC-003, TC-020 (вихід).
 * Запуск: mvn test -DexcludedGroups=none -Dtest=LoginUiTest
 */
@Tag("ui")
@DisplayName("Персонал+ (OrangeHRM): вхід і вихід")
class LoginUiTest extends OrangeHrmUiBase {

    @Test
    @DisplayName("TC-001 (F-10 / GUI-10): сторінка входу містить Login, поля Username/Password, посилання «Forgot your password?»")
    void loginPageHasElementsPerGui10() {
        waitVisible(USERNAME);
        assertAll("GUI-10",
                () -> assertEquals("Login", driver.findElement(By.cssSelector(".orangehrm-login-title")).getText()),
                () -> assertTrue(driver.findElement(USERNAME).isDisplayed()),
                () -> assertEquals("password", driver.findElement(PASSWORD).getAttribute("type"), "пароль має бути прихований"),
                () -> assertTrue(driver.findElement(LOGIN_BUTTON).isDisplayed()),
                () -> assertTrue(driver.findElement(By.cssSelector(".orangehrm-login-forgot-header")).getText()
                        .contains("Forgot your password?")));
    }

    @Test
    @DisplayName("TC-001 (F-10): успішний вхід Admin відкриває Dashboard")
    void validLoginOpensDashboard() {
        login(ADMIN_USER, ADMIN_PASS);
        wait.until(ExpectedConditions.urlContains("/dashboard/index"));
        assertAll("Dashboard",
                () -> assertEquals("Dashboard", waitVisible(MODULE_TITLE).getText()),
                () -> assertTrue(driver.getCurrentUrl().contains("/dashboard/index")));
    }

    @Test
    @DisplayName("TC-002 (F-20): хибний пароль — «Invalid credentials» (текст за F-20), вхід не виконано")
    void wrongPasswordShowsInvalidCredentials() {
        // Увага: GUI-20 вимагає «Wrong username or password» — це розбіжність зафіксовано як BUG-002 (спринт 3).
        // Тест перевіряє текст F-20 («Invalid credentials»), який збігається з фактичною поведінкою demo.
        login(ADMIN_USER, "wrongpass1");
        String text = waitVisible(ALERT_TEXT).getText();
        assertAll("Помилка входу",
                () -> assertEquals("Invalid credentials", text),
                () -> assertTrue(driver.getCurrentUrl().contains("/auth/login"), "має лишитись на сторінці входу"));
    }

    @Test
    @DisplayName("TC-003 (F-20 / GUI-40): порожні поля — «Required» під обома полями, вхід не виконано")
    void emptyFieldsShowRequired() {
        waitVisible(LOGIN_BUTTON).click();
        List<String> errors = wait.until(d -> {
            List<String> texts = d.findElements(FIELD_ERROR).stream().map(e -> e.getText()).toList();
            return texts.size() >= 2 ? texts : null; // чекаємо саме на стан, який перевіряємо
        });
        assertAll("Required",
                () -> assertEquals(2, errors.size()),
                () -> assertTrue(errors.stream().allMatch("Required"::equals), "тексти: " + errors),
                () -> assertTrue(driver.getCurrentUrl().contains("/auth/login")));
    }

    @Test
    @DisplayName("TC-020 (GUI-30): вихід через меню користувача повертає на сторінку входу")
    void logoutReturnsToLogin() {
        loginAsAdmin();
        waitVisible(By.cssSelector(".oxd-userdropdown-tab")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href$='/auth/logout']"))).click();
        wait.until(ExpectedConditions.urlContains("/auth/login"));
        assertTrue(waitVisible(LOGIN_BUTTON).isDisplayed());
    }
}
