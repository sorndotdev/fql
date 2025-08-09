package dev.sorn.fql.api;


import dev.sorn.fql.FiscalYearTestData;
import java.util.function.Function;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static dev.sorn.fql.api.FiscalYear.fiscalYear;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FiscalYearTest implements FiscalYearTestData {
    @ParameterizedTest
    @ValueSource(strings = {"1000", "1999", "2028", "9999"})
    void fiscalYear_valid_string(String value) {
        // given

        // when
        FiscalYear year = fiscalYear(value);

        // then
        assertEquals(year.value(), year.value());
    }

    @ParameterizedTest
    @ValueSource(ints = {1000, 1999, 2028, 9999})
    void fiscalYear_valid_int(int value) {
        // given

        // when
        FiscalYear year = fiscalYear(value);

        // then
        assertEquals(value, year.value());
    }

    @ParameterizedTest
    @ValueSource(strings = {"999", "10000", "abcd", "12", "", " "})
    void fiscalYear_invalid_string_throws(String value) {
        // given
        Function<String, FiscalYear> f = FiscalYear::fiscalYear;

        // when
        var e = assertThrows(FQLError.class, () -> f.apply(value));

        // then
        assertEquals("'fiscalYear.value' does not match '(\\d{4})': " + value, e.getMessage());
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 999})
    void fiscalYear_invalid_int_below_min_throws(int value) {
        // given
        Function<Integer, FiscalYear> f = FiscalYear::fiscalYear;

        // when
        var e = assertThrows(FQLError.class, () -> f.apply(value));

        // then
        assertEquals(String.format("'fiscalYear.value' is below min: %d < 1000", value), e.getMessage());
    }

    @ParameterizedTest
    @ValueSource(ints = {10000, 99999})
    void fiscalYear_invalid_int_above_max_throws(int value) {
        // given
        Function<Integer, FiscalYear> f = FiscalYear::fiscalYear;

        // when
        var e = assertThrows(FQLError.class, () -> f.apply(value));

        // then
        assertEquals(String.format("'fiscalYear.value' is above max: %d > 9999", value), e.getMessage());
    }

    @Test
    void equals_same_true() {
        // given
        FiscalYear fy1 = aFiscalYear(1999);

        // when
        boolean equal = fy1.equals(fy1);

        // then
        assertTrue(equal, String.format("expected: %s == %s", fy1, fy1));
    }

    @Test
    void equals_identical_true() {
        // given
        FiscalYear fy1 = aFiscalYear(1999);
        FiscalYear fy2 = aFiscalYear(1999);

        // when
        boolean equal = fy1.equals(fy2);

        // then
        assertTrue(equal, String.format("expected: %s == %s", fy1, fy2));
    }

    @Test
    void equals_different_value_false() {
        // given
        FiscalYear fy1 = aFiscalYear(1999);
        FiscalYear fy2 = aFiscalYear(2000);

        // when
        boolean equal = fy1.equals(fy2);

        // then
        assertFalse(equal, String.format("expected: %s != %s", fy1, fy2));
    }

    @Test
    void equals_different_instance_false() {
        // given
        FiscalYear fy1 = aFiscalYear(1999);
        Object fy2 = new Object();

        // when
        boolean equal = fy1.equals(fy2);

        // then
        assertFalse(equal, String.format("expected: %s != %s", fy1, fy2));
    }

    @Test
    void equals_null_false() {
        // given
        FiscalYear fy1 = aFiscalYear(1999);
        FiscalYear fy2 = null;

        // when
        boolean equal = fy1.equals(fy2);

        // then
        assertFalse(equal, String.format("expected: %s != %s", fy1, fy2));
    }

    @Test
    void hashCode_is_int_value() {
        // given
        FiscalYear fy = aRandomFiscalYear();

        // when
        int hashCode = fy.hashCode();

        // then
        assertEquals(hashCode, fy.value());
    }
}