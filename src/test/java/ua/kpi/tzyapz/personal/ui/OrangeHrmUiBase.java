package ua.kpi.tzyapz.personal.ui;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Спільна основа UI-тестів «Персонал+» (demo: OrangeHRM). Кожен тест отримує власний браузер (незалежність),
 * driver.quit() — в @AfterEach (виконується і після падіння). Thread.sleep та implicitlyWait не використовуються.
 * Автор: Вилінська Злата (Інженер з тестування).
 */
abstract class OrangeHrmUiBase {

    static final String BASE_URL = "https://opensource-demo.orangehrmlive.com";
    static final String LOGIN_URL = BASE_URL + "/web/index.php/auth/login";
    static final String ADMIN_USER = "Admin";   // F-10 / розділ 2.1 SRS; актуальні дані — на сторінці входу demo
    static final String ADMIN_PASS = "admin123";

    static final By USERNAME = By.cssSelector("input[name='username']");
    static final By PASSWORD = By.cssSelector("input[name='password']");
    static final By LOGIN_BUTTON = By.cssSelector("button[type='submit']");
    static final By ALERT_TEXT = By.cssSelector(".oxd-alert-content-text");
    static final By FIELD_ERROR = By.cssSelector(".oxd-input-field-error-message");
    static final By MODULE_TITLE = By.cssSelector(".oxd-topbar-header-breadcrumb h6");

    WebDriver driver;
    WebDriverWait wait;

    @BeforeEach
    void openBrowser() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new", "--window-size=1280,900");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(20)); // demo повільне — тайм-аут щедрий
        driver.get(LOGIN_URL);
    }

    @AfterEach
    void closeBrowser() {
        if (driver != null) {
            driver.quit();
        }
    }

    WebElement waitVisible(By by) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    void login(String user, String pass) {
        waitVisible(USERNAME).sendKeys(user);
        driver.findElement(PASSWORD).sendKeys(pass);
        driver.findElement(LOGIN_BUTTON).click();
    }

    void loginAsAdmin() {
        login(ADMIN_USER, ADMIN_PASS);
        wait.until(ExpectedConditions.urlContains("/dashboard/index")); // передумова — жорстко
    }
}
