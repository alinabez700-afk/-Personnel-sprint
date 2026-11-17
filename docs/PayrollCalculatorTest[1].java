package ua.kpi.tzyapz.personal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Юніт-тести PayrollCalculator: BL-50 (сума «на руки»), BL-60 (погодинна ставка), BL-70 (понаднормові).
 * Автор: Вилінська Злата (Інженер з тестування). Очікувані значення пораховано вручну за SRS (розділ 5).
 * Гроші порівнюються разом зі шкалою (2 знаки), бо BigDecimal.equals враховує scale.
 */
@Tag("unit")
@DisplayName("PayrollCalculator — нарахування (BL-50…BL-70)")
class PayrollCalculatorTest {

    private PayrollCalculator calc;

    @BeforeEach
    void setUp() {
        calc = new PayrollCalculator();
    }

    private static void assertMoney(String expected, BigDecimal actual) {
        assertNotNull(actual, "результат не має бути null");
        assertAll("гроші: " + expected,
                () -> assertEquals(0, new BigDecimal(expected).compareTo(actual), "очікувалось " + expected + ", отримано " + actual),
                () -> assertEquals(2, actual.scale(), "шкала має бути 2 (копійки), отримано " + actual));
    }

    @ParameterizedTest(name = "BL-50: gross={0} → net={1}")
    @CsvSource({"10000.00, 7700.00", "12345.67, 9506.17", "1000.00, 770.00", "0.00, 0.00"})
    @DisplayName("BL-50: на руки = gross × 0,77 (ПДФО 18 % + військовий збір 5 %)")
    // BUG-009 (docs/sprint4/bug-reports.md): тест правий за BL, лишається червоним на v1
    void netIsGrossTimes077(String gross, String expectedNet) {
        assertMoney(expectedNet, calc.net(new BigDecimal(gross)));
    }

    @Test
    @DisplayName("BL-50: округлення HALF_UP — 100.50 × 0,77 = 77,385 → 77.39")
    // BUG-009 (docs/sprint4/bug-reports.md): тест правий за BL, лишається червоним на v1
    void netRoundsHalfUp() {
        assertMoney("77.39", calc.net(new BigDecimal("100.50")));
    }

    @Test
    @DisplayName("BL-50: мінімальна сума 0.01 → 0.01 (0,0077 округлюється HALF_UP)")
    void netOfOneKopiyka() {
        assertMoney("0.01", calc.net(new BigDecimal("0.01")));
    }

    @Test
    @DisplayName("BL-50: від'ємна або відсутня сума — IllegalArgumentException")
    void netInvalidInputThrows() {
        assertAll("net invalid",
                () -> assertThrows(IllegalArgumentException.class, () -> calc.net(new BigDecimal("-0.01"))),
                () -> assertThrows(IllegalArgumentException.class, () -> calc.net(null)));
    }

    @ParameterizedTest(name = "BL-60: оклад={0} → ставка={1}")
    @CsvSource({"10000.00, 59.52", "25000.00, 148.81", "16800.00, 100.00", "0.00, 0.00"})
    @DisplayName("BL-60: погодинна ставка = оклад / 168, HALF_UP до копійок")
    // BUG-010 (docs/sprint4/bug-reports.md): тест правий за BL, лишається червоним на v1
    void hourlyRateIsMonthlyDividedBy168(String monthly, String expected) {
        assertMoney(expected, calc.hourlyRate(new BigDecimal(monthly)));
    }

    @Test
    @DisplayName("BL-60: від'ємний або відсутній оклад — IllegalArgumentException")
    void hourlyRateInvalidInputThrows() {
        assertAll("hourlyRate invalid",
                () -> assertThrows(IllegalArgumentException.class, () -> calc.hourlyRate(new BigDecimal("-1"))),
                () -> assertThrows(IllegalArgumentException.class, () -> calc.hourlyRate(null)));
    }

    @ParameterizedTest(name = "BL-70: оклад={0}, годин={1} → {2}")
    @CsvSource({"16800.00, 2.5, 375.00", "10000.00, 2, 178.56", "10000.00, 0.5, 44.64", "10000.00, 0, 0.00"})
    @DisplayName("BL-70: понаднормові = округлена погодинна ставка × 1,5 × години (10000/2 год → 178.56, не 178.57)")
    void overtimeUsesRoundedHourlyRate(String monthly, String hours, String expected) {
        assertMoney(expected, calc.overtimePay(new BigDecimal(monthly), new BigDecimal(hours)));
    }

    @Test
    @DisplayName("BL-70: від'ємні години або оклад — IllegalArgumentException")
    void overtimeInvalidInputThrows() {
        assertAll("overtimePay invalid",
                () -> assertThrows(IllegalArgumentException.class, () -> calc.overtimePay(new BigDecimal("10000.00"), new BigDecimal("-0.5"))),
                () -> assertThrows(IllegalArgumentException.class, () -> calc.overtimePay(new BigDecimal("-10000.00"), BigDecimal.ONE)));
    }
}
