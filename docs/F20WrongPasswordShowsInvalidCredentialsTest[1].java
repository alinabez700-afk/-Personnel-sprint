// ЧЕРНЕТКА експорту Selenium IDE (Export -> Java JUnit), тест "F-20 wrong password shows Invalid credentials".
// Не збирається Maven, довідково. Адаптована версія — src/test/java/.../ui/LoginUiTest.java (wrongPasswordShowsInvalidCredentials).
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class F20WrongPasswordShowsInvalidCredentialsTest {
  private WebDriver driver;
  @Before
  public void setUp() { driver = new ChromeDriver(); }
  @After
  public void tearDown() { driver.quit(); }
  @Test
  public void f20WrongPasswordShowsInvalidCredentials() {
    driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
    new WebDriverWait(driver, Duration.ofSeconds(20))
        .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name='username']")));
    driver.findElement(By.cssSelector("input[name='username']")).sendKeys("Admin");
    driver.findElement(By.cssSelector("input[name='password']")).sendKeys("wrong_password_123");
    driver.findElement(By.cssSelector("button[type='submit']")).click();
    new WebDriverWait(driver, Duration.ofSeconds(20))
        .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".oxd-alert-content-text")));
    assertThat(driver.findElement(By.cssSelector(".oxd-alert-content-text")).getText(), is("Invalid credentials"));
  }
}
