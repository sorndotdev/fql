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
        "1999-Q1",
        "1999-Q2",
        "1999-Q3",
        "1999-Q4",
    })
    void fiscalPeriod_from_valid_year_quarter_string(String value) {
        // given

        // when
        Function<String, FiscalPeriod> f = FiscalPeriod::fiscalPeriod;

        // then
        Integer year = parseInt(value.split("-")[0]);
        Integer quarter = parseInt(value.split("-")[1].substring(1));
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
        "1999-Q0",
        "1999-Q5",
        "1999-Q44",
        "-1999-Q1",
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
        FiscalPeriod fp1 = aFiscalPeriod("1999-Q4");

        // when
        boolean equal = fp1.equals(fp1);

        // then
        assertTrue(equal, String.format("expected: %s == %s", fp1, fp1));
    }

    @Test
    void equals_identical_true() {
        // given
        FiscalPeriod fp1 = aFiscalPeriod("1999-Q4");
        FiscalPeriod fp2 = aFiscalPeriod("1999-Q4");

        // when
        boolean equal = fp1.equals(fp2);

        // then
        assertTrue(equal, String.format("expected: %s == %s", fp1, fp2));
    }

    @Test
    void equals_different_value_false() {
        // given
        FiscalPeriod fp1 = aFiscalPeriod("1999-Q4");
        FiscalPeriod fp2 = aFiscalPeriod("1999-Q3");

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
        FiscalPeriod fp1 = aFiscalPeriod("1999-Q4");

        // when
        String string = fp1.toString();

        // then
        assertEquals("1999-Q4", string);
    }

    @Test
    void hashCode_magicNumber() {
        // given
        FiscalPeriod fp = aFiscalPeriod("1999-Q2");

        // when
        int actualHash = fp.hashCode();

        // then
        int expectedHash = 61971;
        assertEquals(expectedHash, actualHash);
    }
}