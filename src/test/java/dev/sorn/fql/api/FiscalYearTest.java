package dev.sorn.fql.api;


import dev.sorn.fql.FiscalYearTestData;
import java.util.function.Function;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static dev.sorn.fql.api.FiscalYear.fiscalYear;
import static java.lang.Integer.parseInt;
import static java.lang.String.format;
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
        FiscalYear fy = fiscalYear(value);

        // then
        assertEquals(parseInt(value), fy.value());
    }

    @ParameterizedTest
    @ValueSource(ints = {1000, 1999, 2028, 9999})
    void fiscalYear_valid_int(int value) {
        // given

        // when
        FiscalYear fy = fiscalYear(value);

        // then
        assertEquals(value, fy.value());
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
        assertEquals(format("'fiscalYear.value' is below min: %d < 1000", value), e.getMessage());
    }

    @ParameterizedTest
    @ValueSource(ints = {10000, 99999})
    void fiscalYear_invalid_int_above_max_throws(int value) {
        // given
        Function<Integer, FiscalYear> f = FiscalYear::fiscalYear;

        // when
        var e = assertThrows(FQLError.class, () -> f.apply(value));

        // then
        assertEquals(format("'fiscalYear.value' is above max: %d > 9999", value), e.getMessage());
    }

    @Test
    void equals_same_true() {
        // given
        FiscalYear fy1 = aFiscalYear(1999);

        // when
        boolean equal = fy1.equals(fy1);

        // then
        assertTrue(equal, format("expected: %s == %s", fy1, fy1));
    }

    @Test
    void equals_identical_true() {
        // given
        FiscalYear fy1 = aFiscalYear(1999);
        FiscalYear fy2 = aFiscalYear(1999);

        // when
        boolean equal = fy1.equals(fy2);

        // then
        assertTrue(equal, format("expected: %s == %s", fy1, fy2));
    }

    @Test
    void equals_different_value_false() {
        // given
        FiscalYear fy1 = aFiscalYear(1999);
        FiscalYear fy2 = aFiscalYear(2000);

        // when
        boolean equal = fy1.equals(fy2);

        // then
        assertFalse(equal, format("expected: %s != %s", fy1, fy2));
    }

    @Test
    void equals_different_instance_false() {
        // given
        FiscalYear fy1 = aFiscalYear(1999);
        Object fy2 = new Object();

        // when
        boolean equal = fy1.equals(fy2);

        // then
        assertFalse(equal, format("expected: %s != %s", fy1, fy2));
    }

    @Test
    void equals_null_false() {
        // given
        FiscalYear fy1 = aFiscalYear(1999);
        FiscalYear fy2 = null;

        // when
        boolean equal = fy1.equals(fy2);

        // then
        assertFalse(equal, format("expected: %s != %s", fy1, fy2));
    }

    @ParameterizedTest
    @ValueSource(strings = {"1000", "1994", "1999", "4200"})
    void toString_returns_correct_format(String value) {
        // given

        // when
        FiscalYear fy = fiscalYear(value);

        // then
        assertEquals(value, fy.toString(), "bad string representation: " + fy);
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

    @Test
    void compareTo_equal_year_periods_returns_zero() {
        // given
        FiscalYear fq1 = aFiscalYear(1999);
        FiscalYear fq2 = aFiscalYear(1999);

        // when
        int comparison = fq1.compareTo(fq2);

        // then
        assertEquals(0, comparison, String.format("expected: %s == %s", fq1, fq2));
    }

    @Test
    void compareTo_earlier_year_returns_negative() {
        // given
        FiscalYear earlier = aFiscalYear(1999);
        FiscalYear later = aFiscalYear(2000);

        // when
        int comparison = earlier.compareTo(later);

        // then
        assertTrue(comparison < 0, String.format("expected: %s < %s", earlier, later));
    }

    @Test
    void compareTo_later_year_returns_positive() {
        // given
        FiscalYear earlier = aFiscalYear(1999);
        FiscalYear later = aFiscalYear(2000);

        // when
        int comparison = later.compareTo(earlier);

        // then
        assertTrue(comparison > 0, String.format("expected: %s > %s", later, earlier));
    }
}