// ЧЕРНЕТКА експорту Selenium IDE (Export -> Java JUnit) з selenium-ide/personal.side, тест "F-10 valid login opens Dashboard".
// Не збирається Maven (лежить поза src/), довідково. Робоча адаптована версія — src/test/java/.../ui/LoginUiTest.java:
//   JUnit 4 (@Before/@After, org.junit.Test, Hamcrest) -> JUnit 5; pause/Thread.sleep -> WebDriverWait;
//   додано headless (--headless=new, --window-size=1280,900), @Tag("ui"), @DisplayName із ID TC і вимоги.
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

public class F10ValidLoginOpensDashboardTest {
  private WebDriver driver;
  @Before
  public void setUp() { driver = new ChromeDriver(); }
  @After
  public void tearDown() { driver.quit(); }
  @Test
  public void f10ValidLoginOpensDashboard() {
    driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
    new WebDriverWait(driver, Duration.ofSeconds(20))
        .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name='username']")));
    driver.findElement(By.cssSelector("input[name='username']")).sendKeys("Admin");
    driver.findElement(By.cssSelector("input[name='password']")).sendKeys("admin123");
    driver.findElement(By.cssSelector("button[type='submit']")).click();
    new WebDriverWait(driver, Duration.ofSeconds(20))
        .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".oxd-topbar-header-breadcrumb h6")));
    assertThat(driver.findElement(By.cssSelector(".oxd-topbar-header-breadcrumb h6")).getText(), is("Dashboard"));
    // verifyElementPresent css=.oxd-userdropdown-tab (м'яка перевірка в .side)
    assertTrue(driver.findElements(By.cssSelector(".oxd-userdropdown-tab")).size() > 0);
  }
}
