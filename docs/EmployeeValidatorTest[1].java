package ua.kpi.tzyapz.personal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Юніт-тести EmployeeValidator (BL-80): email, РНОКПП, дата найму, вік на дату найму, validate().
 * Автор: Безугла Аліна (Керівник тестування). Очікувані значення — з SRS, дати фіксовані (today = 2026-10-28).
 */
@Tag("unit")
@DisplayName("EmployeeValidator — перевірка даних працівника (BL-80)")
class EmployeeValidatorTest {

    private static final LocalDate TODAY = LocalDate.of(2026, 10, 28);
    private EmployeeValidator validator;

    @BeforeEach
    void setUp() {
        validator = new EmployeeValidator();
    }

    private static Set<String> codes(Collection<?> result) {
        return result.stream().map(String::valueOf).collect(Collectors.toSet());
    }

    @ParameterizedTest(name = "BL-80: коректний email {0}")
    @ValueSource(strings = {"t@example.org", "ivan.petrenko@example.com", "a_b%c+d-e@sub.example.co.ua", "user1@my-domain.ua"})
    @DisplayName("BL-80: коректні email приймаються")
    void validEmailsAccepted(String email) {
        assertTrue(validator.isValidEmail(email), "має бути коректним: " + email);
    }

    @ParameterizedTest(name = "BL-80: некоректний email «{0}»")
    @ValueSource(strings = {"plainaddress", "@example.com", "user@", "user@example", "user@example.c", "user@@example.com",
            "user name@example.com", "user@exam_ple.com", "user@example.123"})
    @DisplayName("BL-80: некоректні email відхиляються (без @, без домену/зони, зона < 2 літер, пробіл тощо)")
    void invalidEmailsRejected(String email) {
        assertFalse(validator.isValidEmail(email), "має бути некоректним: " + email);
    }

    @Test
    @DisplayName("BL-80: РНОКПП — рівно 10 цифр")
    // BUG-011 (docs/sprint4/bug-reports.md): тест правий за BL, лишається червоним на v1
    void taxIdExactlyTenDigits() {
        assertAll("isValidTaxId",
                () -> assertTrue(validator.isValidTaxId("1234567890")),
                () -> assertTrue(validator.isValidTaxId("0000000000")),
                () -> assertFalse(validator.isValidTaxId("123456789"), "9 цифр"),
                () -> assertFalse(validator.isValidTaxId("12345678901"), "11 цифр"));
    }

    @ParameterizedTest(name = "BL-80: РНОКПП «{0}» некоректний")
    @ValueSource(strings = {"", "12345abcde", "1234 56789", "123456789-", "+123456789"})
    @DisplayName("BL-80: РНОКПП з літерами, пробілами, знаками або порожній — відхиляється")
    void taxIdWithOtherSymbolsRejected(String taxId) {
        assertFalse(validator.isValidTaxId(taxId));
    }

    @Test
    @DisplayName("BL-80: дата найму не пізніша за поточну (сьогодні — дозволено, завтра — ні)")
    void hireDateNotInFuture() {
        assertAll("isHireDateValid",
                () -> assertTrue(validator.isHireDateValid(TODAY, TODAY), "сьогодні"),
                () -> assertTrue(validator.isHireDateValid(TODAY.minusDays(1), TODAY), "вчора"),
                () -> assertFalse(validator.isHireDateValid(TODAY.plusDays(1), TODAY), "завтра"));
    }

    @ParameterizedTest(name = "BL-80: народження {0}, найм {1} → {2}")
    @CsvSource({"2010-09-15, 2026-09-15, true", "2010-09-14, 2026-09-15, true", "2010-09-16, 2026-09-15, false",
            "2008-02-29, 2024-02-29, true", "2008-02-29, 2024-02-28, false", "1990-05-20, 2026-09-01, true"})
    @DisplayName("BL-80: на дату найму має бути ≥ 16 повних років (у день 16-річчя — вже можна)")
    // BUG-012 (docs/sprint4/bug-reports.md): тест правий за BL, лишається червоним на v1
    void minimumAgeSixteenOnHireDate(LocalDate birth, LocalDate hire, boolean expected) {
        assertEquals(expected, validator.isOldEnough(birth, hire));
    }

    @Test
    @DisplayName("BL-80: validate() для коректних даних повертає порожній список")
    void validateAllCorrectReturnsEmpty() {
        assertTrue(validator.validate("a@example.com", "1234567890",
                LocalDate.of(1990, 5, 20), LocalDate.of(2026, 9, 1), TODAY).isEmpty());
    }

    @Test
    @DisplayName("BL-80: validate() повертає коди EMAIL, TAX_ID, HIRE_DATE, AGE для всіх помилок")
    // BUG-011 (docs/sprint4/bug-reports.md): тест правий за BL, лишається червоним на v1
    void validateAllWrongReturnsAllCodes() {
        // найм у майбутньому (2026-11-01 > today) і вік на нього 14 повних років (народження 2012-01-01)
        var result = validator.validate("bad", "123", LocalDate.of(2012, 1, 1), LocalDate.of(2026, 11, 1), TODAY);
        assertAll("validate all wrong",
                () -> assertEquals(Set.of("EMAIL", "TAX_ID", "HIRE_DATE", "AGE"), codes(result)),
                () -> assertEquals(4, result.size(), "кожен код — один раз"));
    }

    @Test
    @DisplayName("BL-80: validate() — лише некоректний email дає єдиний код EMAIL")
    void validateOnlyEmailWrong() {
        var result = validator.validate("not-an-email", "1234567890",
                LocalDate.of(1990, 5, 20), LocalDate.of(2026, 9, 1), TODAY);
        assertEquals(Set.of("EMAIL"), codes(result));
    }

    @Test
    @DisplayName("BL-80: validate() — відсутня дата кидає IllegalArgumentException")
    void validateMissingDateThrows() {
        LocalDate d = LocalDate.of(2000, 1, 1);
        assertAll("validate null dates",
                () -> assertThrows(IllegalArgumentException.class, () -> validator.validate("a@example.com", "1234567890", null, d, TODAY)),
                () -> assertThrows(IllegalArgumentException.class, () -> validator.validate("a@example.com", "1234567890", d, null, TODAY)),
                () -> assertThrows(IllegalArgumentException.class, () -> validator.validate("a@example.com", "1234567890", d, d, null)));
    }
}
