package dev.sorn.fql.api;

import dev.sorn.fql.FiscalPeriodTestData;
import java.util.function.Function;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static java.lang.Integer.parseInt;
import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FiscalPeriodTest implements FiscalPeriodTestData {
    @ParameterizedTest
    @ValueSource(strings = {
        "1000",
        "1999",
        "2028",
        "9999",
    })
    void fiscalPeriod_valid_year_string(String value) {
        // given

        // when
        Function<String, FiscalPeriod> f = FiscalPeriod::fiscalPeriod;

        // then
        Integer year = parseInt(value);
        FiscalPeriod period = assertDoesNotThrow(() -> f.apply(value));
        assertEquals(year, period.fiscalYear().value());
        assertTrue(period.fiscalQuarter().isEmpty());
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "1999Q1",
        "1999Q2",
        "1999Q3",
        "1999Q4",
    })
    void fiscalPeriod_from_valid_year_quarter_string(String value) {
        // given

        // when
        Function<String, FiscalPeriod> f = FiscalPeriod::fiscalPeriod;

        // then
        Integer year = parseInt(value.substring(0, 4));
        Integer quarter = parseInt(value.substring(5));
        FiscalPeriod period = assertDoesNotThrow(() -> f.apply(value));
        assertTrue(period.fiscalQuarter().isPresent());
        assertEquals(year, period.fiscalYear().value());
        assertEquals(period.fiscalQuarter().map(FiscalQuarter::value).get(), quarter);
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "",
        "abc",
        "999",
        "202-01",
        "1999Q0",
        "1999Q5",
        "1999Q44",
        "-1999Q1",
        "1999-Q1-",
    })
    void fiscalPeriod_from_invalid_string_throws(String value) {
        // given

        // when
        Function<String, FiscalPeriod> f = FiscalPeriod::fiscalPeriod;

        // then
        assertThrows(FQLError.class, () -> f.apply(value));
    }

    @Test
    void equals_same_true() {
        // given
        FiscalPeriod fp1 = aFiscalPeriod("1999Q4");

        // when
        boolean equal = fp1.equals(fp1);

        // then
        assertTrue(equal, String.format("expected: %s == %s", fp1, fp1));
    }

    @Test
    void equals_identical_true() {
        // given
        FiscalPeriod fp1 = aFiscalPeriod("1999Q4");
        FiscalPeriod fp2 = aFiscalPeriod("1999Q4");

        // when
        boolean equal = fp1.equals(fp2);

        // then
        assertTrue(equal, String.format("expected: %s == %s", fp1, fp2));
    }

    @Test
    void equals_different_value_false() {
        // given
        FiscalPeriod fp1 = aFiscalPeriod("1999Q4");
        FiscalPeriod fp2 = aFiscalPeriod("1999Q3");

        // when
        boolean equal = fp1.equals(fp2);

        // then
        assertFalse(equal, String.format("expected: %s != %s", fp1, fp2));
    }

    @Test
    void equals_different_instance_false() {
        // given
        FiscalPeriod fp1 = aRandomFiscalPeriod();
        Object fp2 = new Object();

        // when
        boolean equal = fp1.equals(fp2);

        // then
        assertFalse(equal, format("expected: %s != %s", fp1, fp2));
    }

    @Test
    void equals_null_false() {
        // given
        FiscalPeriod fp1 = aRandomFiscalPeriod();
        FiscalPeriod fp2 = null;

        // when
        boolean equal = fp1.equals(fp2);

        // then
        assertFalse(equal, format("expected: %s != %s", fp1, fp2));
    }

    @Test
    void toString_year() {
        // given
        FiscalPeriod fp1 = aFiscalPeriod("1999");

        // when
        String string = fp1.toString();

        // then
        assertEquals("1999", string);
    }

    @Test
    void toString_year_quarter() {
        // given
        FiscalPeriod fp1 = aFiscalPeriod("1999Q4");

        // when
        String string = fp1.toString();

        // then
        assertEquals("1999Q4", string);
    }

    @Test
    void hashCode_magicNumber() {
        // given
        FiscalPeriod fp = aFiscalPeriod("1999Q2");

        // when
        int actualHash = fp.hashCode();

        // then
        int expectedHash = 61971;
        assertEquals(expectedHash, actualHash);
    }

    @Test
    void compareTo_equal_year_periods_returns_zero() {
        // given
        FiscalPeriod fp1 = aFiscalPeriod("1999");
        FiscalPeriod fp2 = aFiscalPeriod("1999");

        // when
        int comparison = fp1.compareTo(fp2);

        // then
        assertEquals(0, comparison, String.format("expected: %s == %s", fp1, fp2));
    }

    @Test
    void compareTo_equal_year_quarter_periods_returns_zero() {
        // given
        FiscalPeriod fp1 = aFiscalPeriod("1999Q4");
        FiscalPeriod fp2 = aFiscalPeriod("1999Q4");

        // when
        int comparison = fp1.compareTo(fp2);

        // then
        assertEquals(0, comparison, String.format("expected: %s == %s", fp1, fp2));
    }

    @Test
    void compareTo_earlier_year_returns_negative() {
        // given
        FiscalPeriod earlier = aFiscalPeriod("1999");
        FiscalPeriod later = aFiscalPeriod("2000");

        // when
        int comparison = earlier.compareTo(later);

        // then
        assertTrue(comparison < 0, String.format("expected: %s < %s", earlier, later));
    }

    @Test
    void compareTo_earlier_year_quarter_returns_negative() {
        // given
        FiscalPeriod earlier = aFiscalPeriod("1999Q3");
        FiscalPeriod later = aFiscalPeriod("1999Q4");

        // when
        int comparison = earlier.compareTo(later);

        // then
        assertTrue(comparison < 0, String.format("expected: %s < %s", earlier, later));
    }

    @Test
    void compareTo_later_year_returns_positive() {
        // given
        FiscalPeriod earlier = aFiscalPeriod("1999");
        FiscalPeriod later = aFiscalPeriod("2000");

        // when
        int comparison = later.compareTo(earlier);

        // then
        assertTrue(comparison > 0, String.format("expected: %s > %s", later, earlier));
    }

    @Test
    void compareTo_later_year_quarter_returns_positive() {
        // given
        FiscalPeriod earlier = aFiscalPeriod("1999Q3");
        FiscalPeriod later = aFiscalPeriod("1999Q4");

        // when
        int comparison = later.compareTo(earlier);

        // then
        assertTrue(comparison > 0, String.format("expected: %s > %s", later, earlier));
    }

    @Test
    void compareTo_year_only_vs_year_quarter_returns_zero() {
        // given
        FiscalPeriod yearOnly = aFiscalPeriod("1999");
        FiscalPeriod yearQuarter = aFiscalPeriod("1999Q1");

        // when
        int comparison = yearOnly.compareTo(yearQuarter);

        // then
        assertEquals(0, comparison, format("expected: %s == %s", yearOnly, yearQuarter));
    }
}