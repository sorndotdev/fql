package dev.sorn.fql.api;

import dev.sorn.fql.FiscalQuarterTestData;
import java.util.function.Function;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static dev.sorn.fql.api.FiscalQuarter.fiscalQuarter;
import static java.lang.Integer.parseInt;
import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FiscalQuarterTest implements FiscalQuarterTestData {
    @ParameterizedTest
    @ValueSource(strings = {"Q1", "Q2", "Q3", "Q4"})
    void fiscalQuarter_valid_string(String value) {
        // given

        // when
        FiscalQuarter fq = fiscalQuarter(value);

        // then
        assertEquals(parseInt(value.substring(1)), fq.value());
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4})
    void fiscalQuarter_valid_int(int value) {
        // given

        // when
        FiscalQuarter fq = fiscalQuarter(value);

        // then
        assertEquals(value, fq.value());
    }

    @ParameterizedTest
    @ValueSource(strings = {"Q0", "Q5", "Q44", "Q", "q1", "1"})
    void fiscalQuarter_invalid_string_throws(String value) {
        // given
        Function<String, FiscalQuarter> f = FiscalQuarter::fiscalQuarter;

        // when
        var e = assertThrows(FQLError.class, () -> f.apply(value));

        // then
        assertEquals(format("[FiscalQuarter#fiscalQuarter] '%s' does not match Q([1-4])", value), e.getMessage());
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0})
    void fiscalQuarter_invalid_int_below_min_throws(int value) {
        // given
        Function<Integer, FiscalQuarter> f = FiscalQuarter::fiscalQuarter;

        // when
        var e = assertThrows(FQLError.class, () -> f.apply(value));

        // then
        assertEquals(format("[FiscalQuarter#<init>] '%s' is below min '1'", value), e.getMessage());
    }

    @ParameterizedTest
    @ValueSource(ints = {5, 10})
    void fiscalQuarter_invalid_int_above_max_throws(int value) {
        // given
        Function<Integer, FiscalQuarter> f = FiscalQuarter::fiscalQuarter;

        // when
        var e = assertThrows(FQLError.class, () -> f.apply(value));

        // then
        assertEquals(format("[FiscalQuarter#<init>] '%d' is above max '4'", value), e.getMessage());
    }

    @Test
    void equals_identical_true() {
        // given
        FiscalQuarter fq1 = aFiscalQuarter(4);
        FiscalQuarter fq2 = aFiscalQuarter(4);

        // when
        boolean equal = fq1.equals(fq2);

        // then
        assertTrue(equal, format("expected: %s == %s", fq1, fq2));
    }

    @Test
    void equals_same_true() {
        // given
        FiscalQuarter fq1 = aFiscalQuarter(4);

        // when
        boolean equal = fq1.equals(fq1);

        // then
        assertTrue(equal, format("expected: %s == %s", fq1, fq1));
    }

    @Test
    void equals_different_value_false() {
        // given
        FiscalQuarter fq1 = aFiscalQuarter(4);
        FiscalQuarter fq2 = aFiscalQuarter(1);

        // when
        boolean equal = fq1.equals(fq2);

        // then
        assertFalse(equal, format("expected: %s != %s", fq1, fq2));
    }

    @Test
    void equals_different_instance_false() {
        // given
        FiscalQuarter fq1 = aFiscalQuarter(4);
        Object fq2 = new Object();

        // when
        boolean equal = fq1.equals(fq2);

        // then
        assertFalse(equal, format("expected: %s != %s", fq1, fq2));
    }

    @Test
    void equals_null_false() {
        // given
        FiscalQuarter fq1 = aFiscalQuarter(4);
        FiscalQuarter fq2 = null;

        // when
        boolean equal = fq1.equals(fq2);

        // then
        assertFalse(equal, format("expected: %s != %s", fq1, fq2));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Q1", "Q2", "Q3", "Q4"})
    void toString_returns_correct_format(String value) {
        // given

        // when
        FiscalQuarter fq = fiscalQuarter(value);

        // then
        assertEquals(value, fq.toString(), "bad string representation: " + fq);
    }

    @Test
    void hashCode_is_int_value() {
        // given
        FiscalQuarter fq = aRandomFiscalQuarter();

        // when
        int hashCode = fq.hashCode();

        // then
        assertEquals(hashCode, fq.value());
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4})
    void compareTo_equal_year_periods_returns_zero(int value) {
        // given
        FiscalQuarter fq1 = aFiscalQuarter(value);
        FiscalQuarter fq2 = aFiscalQuarter(value);

        // when
        int comparison = fq1.compareTo(fq2);

        // then
        assertEquals(0, comparison, String.format("expected: %s == %s", fq1, fq2));
    }

    @Test
    void compareTo_earlier_year_returns_negative() {
        // given
        FiscalQuarter earlier = aFiscalQuarter(1);
        FiscalQuarter later = aFiscalQuarter(2);

        // when
        int comparison = earlier.compareTo(later);

        // then
        assertTrue(comparison < 0, String.format("expected: %s < %s", earlier, later));
    }

    @Test
    void compareTo_later_year_returns_positive() {
        // given
        FiscalQuarter earlier = aFiscalQuarter(1);
        FiscalQuarter later = aFiscalQuarter(2);

        // when
        int comparison = later.compareTo(earlier);

        // then
        assertTrue(comparison > 0, String.format("expected: %s > %s", later, earlier));
    }
}