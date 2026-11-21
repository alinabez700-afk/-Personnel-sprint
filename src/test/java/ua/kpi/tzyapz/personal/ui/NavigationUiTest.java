package ua.kpi.tzyapz.personal.ui;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * UI-сценарії після входу: TC-020 (бічне меню, GUI-30), TC-006 (список працівників, F-40/GUI-60),
 * TC-014 (відкриття Leave List, F-90 — лише перший крок; повне погодження заявки лишається ручним кейсом).
 * Дані demo спільні — кількість записів у перевірках не використовується.
 */
@Tag("ui")
@DisplayName("Персонал+ (OrangeHRM): навігація, PIM, Leave")
class NavigationUiTest extends OrangeHrmUiBase {

    @Test
    @DisplayName("TC-020 (GUI-30): бічне меню містить усі пункти модулів для ролі Admin")
    void sidebarContainsModules() {
        loginAsAdmin();
        List<String> items = wait.until(d -> {
            List<String> t = d.findElements(By.cssSelector(".oxd-main-menu-item--name")).stream()
                    .map(WebElement::getText).toList();
            return t.size() >= 12 ? t : null;
        });
        List<String> required = List.of("Admin", "PIM", "Leave", "Time", "Recruitment", "My Info",
                "Performance", "Dashboard", "Directory", "Maintenance", "Claim", "Buzz");
        assertTrue(items.containsAll(required), "відсутні пункти меню; є: " + items);
    }

    @Test
    @DisplayName("TC-006 (F-40 / GUI-60): PIM відкриває список працівників із лічильником «Records Found»")
    void pimOpensEmployeeList() {
        loginAsAdmin();
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href$='/pim/viewPimModule']"))).click();
        wait.until(ExpectedConditions.urlContains("/pim/viewEmployeeList"));
        assertAll("PIM → Employee List",
                () -> assertEquals("PIM", waitVisible(MODULE_TITLE).getText()),
                () -> assertTrue(waitVisible(By.xpath("//span[contains(@class,'oxd-text') and contains(.,'Records Found')]")).isDisplayed()));
    }

    @Test
    @DisplayName("TC-014 (F-90), крок 1: Leave відкриває Leave List зі статусами заявок у фільтрі")
    void leaveOpensLeaveList() {
        loginAsAdmin();
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href$='/leave/viewLeaveModule']"))).click();
        wait.until(ExpectedConditions.urlContains("/leave/viewLeaveList"));
        assertAll("Leave → Leave List",
                () -> assertEquals("Leave", waitVisible(MODULE_TITLE).getText()),
                () -> assertTrue(waitVisible(By.xpath("//label[normalize-space()='Show Leave with Status']")).isDisplayed()));
    }
}
