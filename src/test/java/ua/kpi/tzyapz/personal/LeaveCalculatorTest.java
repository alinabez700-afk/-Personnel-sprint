package ua.kpi.tzyapz.personal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Юніт-тести LeaveCalculator: BL-10 (нарахування), BL-20 (перенесення), BL-30 (робочі дні), BL-40 (чи можна подати заявку).
 * Автор: Морквич Юлія (Тест-аналітик). Очікувані значення — з розділу 5 SRS, а не з коду модуля.
 * Дати: 07.09.2026 — понеділок, 11.09 — п'ятниця, 12–13.09 — вихідні, 14.09 — понеділок, 18.09 — п'ятниця.
 */
@Tag("unit")
@DisplayName("LeaveCalculator — відпустки (BL-10…BL-40)")
class LeaveCalculatorTest {

    private LeaveCalculator calc;

    @BeforeEach
    void setUp() {
        calc = new LeaveCalculator();
    }

    @ParameterizedTest(name = "BL-10: {0} міс. → {1} дн.")
    @CsvSource({"0, 0", "1, 2", "5, 10", "12, 24", "13, 24", "100, 24"})
    @DisplayName("BL-10: 2 дні за місяць, але не більше 24 на рік (межа 12/13 місяців)")
    // BUG-007 (docs/sprint4/bug-reports.md): тест правий за BL, лишається червоним на v1
    void accruedIsTwoPerMonthCappedAt24(int months, int expectedDays) {
        assertEquals(expectedDays, calc.accrued(months));
    }

    @Test
    @DisplayName("BL-10: від'ємна кількість місяців — IllegalArgumentException")
    void accruedNegativeMonthsThrows() {
        assertThrows(IllegalArgumentException.class, () -> calc.accrued(-1));
    }

    @ParameterizedTest(name = "BL-20: невикористано {0} → перенесено {1}")
    @CsvSource({"0, 0", "3, 3", "4, 4", "5, 5", "6, 5", "9, 5"})
    @DisplayName("BL-20: переноситься min(unused, 5) днів (межа 5/6)")
    void carryOverIsCappedAtFive(int unused, int expected) {
        assertEquals(expected, calc.carryOver(unused));
    }

    @Test
    @DisplayName("BL-20: від'ємна кількість невикористаних днів — IllegalArgumentException")
    void carryOverNegativeThrows() {
        assertThrows(IllegalArgumentException.class, () -> calc.carryOver(-1));
    }

    @Test
    @DisplayName("BL-30: пн–пт = 5 робочих днів; один день (середа) = 1")
    // BUG-008 (docs/sprint4/bug-reports.md): тест правий за BL, лишається червоним на v1
    void durationWithinWorkWeek() {
        assertAll("durationDays",
                () -> assertEquals(5, calc.durationDays(LocalDate.of(2026, 9, 7), LocalDate.of(2026, 9, 11))),
                () -> assertEquals(1, calc.durationDays(LocalDate.of(2026, 9, 9), LocalDate.of(2026, 9, 9))));
    }

    @Test
    @DisplayName("BL-30: суботи й неділі не враховуються (пт–пн = 2; два тижні = 10; сб–пн = 1)")
    // BUG-008 (docs/sprint4/bug-reports.md): тест правий за BL, лишається червоним на v1
    void durationSkipsWeekends() {
        assertAll("durationDays",
                () -> assertEquals(2, calc.durationDays(LocalDate.of(2026, 9, 11), LocalDate.of(2026, 9, 14))),
                () -> assertEquals(10, calc.durationDays(LocalDate.of(2026, 9, 7), LocalDate.of(2026, 9, 18))),
                () -> assertEquals(1, calc.durationDays(LocalDate.of(2026, 9, 12), LocalDate.of(2026, 9, 14))));
    }

    @Test
    @DisplayName("BL-30: проміжок лише з вихідних (сб–нд) = 0 робочих днів")
    void durationWeekendOnlyIsZero() {
        assertEquals(0, calc.durationDays(LocalDate.of(2026, 9, 12), LocalDate.of(2026, 9, 13)));
    }

    @Test
    @DisplayName("BL-30: початок пізніше за кінець або відсутня дата — IllegalArgumentException")
    void durationInvalidInputThrows() {
        LocalDate d = LocalDate.of(2026, 9, 9);
        assertAll("durationDays invalid",
                () -> assertThrows(IllegalArgumentException.class, () -> calc.durationDays(LocalDate.of(2026, 9, 10), d)),
                () -> assertThrows(IllegalArgumentException.class, () -> calc.durationDays(null, d)),
                () -> assertThrows(IllegalArgumentException.class, () -> calc.durationDays(d, null)));
    }

    @ParameterizedTest(name = "BL-40: баланс={0}, тривалість={1} → {2}")
    @CsvSource({"5, 5, true", "5, 4, true", "5, 1, true", "5, 6, false", "5, 0, false", "0, 1, false", "10, 3, true"})
    @DisplayName("BL-40: true лише якщо 1 ≤ тривалість ≤ баланс (межі: =баланс, баланс+1, 0)")
    void canRequestBoundaries(int balance, int duration, boolean expected) {
        assertEquals(expected, calc.canRequest(balance, duration));
    }
}
