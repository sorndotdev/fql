package dev.sorn.fql.api;

import dev.sorn.fql.PercentageTestData;
import java.math.BigDecimal;
import java.util.function.Function;
import java.util.regex.Pattern;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static dev.sorn.fql.api.Percentage.percentage;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PercentageTest implements PercentageTestData {
    @Test
    void constants_have_expected_values() {
        assertEquals(BigDecimal.ZERO, Percentage.ZERO.value());
        assertEquals(BigDecimal.ONE, Percentage.ONE.value());
        assertEquals(new BigDecimal("100"), Percentage.HUNDRED.value());
        assertEquals(new BigDecimal("-1E+7"), Percentage.MIN_VALUE.value());
        assertEquals(new BigDecimal("1E+7"), Percentage.MAX_VALUE.value());
    }

    @ParameterizedTest
    @ValueSource(doubles = {1, 1.0, 0.5, .5, 100, 0.1, 0.01, 0.001, 10000000, -10000000})
    void percentage_valid_double_succeeds(double value) {
        // when
        Function<Double, Percentage> f = Percentage::percentage;

        // then
        Percentage pct = assertDoesNotThrow(() -> f.apply(value));
        assertEquals(value, pct.doubleValue());
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "1.0", "1%", "0.5", ".5", "100%", "0.1", "0.01", "0.001", "10000000", "-10000000"})
    void percentage_valid_string_succeeds(String value) {
        // when
        Function<String, Percentage> f = Percentage::percentage;

        // then
        assertNotNull(assertDoesNotThrow(() -> f.apply(value)));
    }

    @ParameterizedTest
    @ValueSource(strings = {"3", "4.2", "7%", "4.2%", "-3", "-4.2", "-7%", "-4.2%", ".3", "-.3", "0.3%", "10000000", "-10000000"})
    void pattern_matches_valid_strings(String value) {
        // given
        Pattern pattern = Percentage.PATTERN;

        // when
        boolean matches = pattern.matcher(value).find();

        // then
        assertTrue(matches, "Pattern should match: " + value);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "mmm", "1..3", "1.0.3", "1%%", "1E5", "1,000", "--1", "1-", "%", "-%", ".", "-1+E3%", "1+E3%"})
    void pattern_rejects_invalid_strings(String value) {
        // given
        Pattern pattern = Percentage.PATTERN;

        // when
        boolean matches = pattern.matcher(value).find();

        // then
        assertFalse(matches, "Pattern should not match: " + value);
    }

    @Test
    void percentage_string_null_throws() {
        // given
        String value = null;

        // when
        FQLError e = assertThrows(FQLError.class, () -> percentage(value));

        // then
        assertEquals("[Percentage#percentage]: 'value' is required", e.getMessage());
    }

    @Test
    void percentage_string_exceeding_max_throws() {
        // given
        String value = "10000001";

        // when
        FQLError e = assertThrows(FQLError.class, () -> percentage(value));

        // then
        assertEquals("[Percentage#<init>] '10000001' is above max '1E+7'", e.getMessage());
    }

    @Test
    void percentage_string_below_min_throws() {
        // given
        String value = "-10000001";

        // when
        FQLError e = assertThrows(FQLError.class, () -> percentage(value));

        // then
        assertEquals("[Percentage#<init>] '-10000001' is below min '-1E+7'", e.getMessage());
    }

    @Test
    void percentage_string_with_percent_conversion() {
        // given
        String value = "50%";

        // when
        Percentage p = percentage(value);

        // then
        assertEquals(new BigDecimal("0.5"), p.value());
    }

    @Test
    void percentage_string_without_percent() {
        // given
        String value = "0.5";

        // when
        Percentage p = percentage(value);

        // then
        assertEquals(new BigDecimal("0.5"), p.value());
    }

    @Test
    void percentage_positive_string() {
        // given
        String value = "42%";

        // when
        Percentage p = percentage(value);

        // then
        assertEquals(new BigDecimal("0.42"), p.value());
    }

    @Test
    void percentage_negative_string() {
        // given
        String value = "-42%";

        // when
        Percentage p = percentage(value);

        // then
        assertEquals(new BigDecimal("-0.42"), p.value());
    }

    @Test
    void percentage_string_with_leading_trailing_spaces() {
        // given
        String value = "  50.5%  ";

        // when
        Percentage p = percentage(value);

        // then
        assertEquals(new BigDecimal("0.505"), p.value());
    }

    @Test
    void add_two_percentages() {
        // given
        Percentage p1 = percentage("10%");
        Percentage p2 = percentage("20%");

        // when
        Percentage result = p1.add(p2);

        // then
        assertEquals(new BigDecimal("0.3"), result.value());
    }

    @Test
    void sub_two_percentages() {
        // given
        Percentage p1 = percentage("30%");
        Percentage p2 = percentage("10%");

        // when
        Percentage result = p1.sub(p2);

        // then
        assertEquals(new BigDecimal("0.2"), result.value());
    }

    @Test
    void mul_two_percentages() {
        // given
        Percentage p1 = percentage("50%");
        Percentage p2 = percentage("50%");

        // when
        Percentage result = p1.mul(p2);

        // then
        assertEquals(new BigDecimal("0.25"), result.value());
    }

    @Test
    void div_two_percentages() {
        // given
        Percentage p1 = percentage("1.0");
        Percentage p2 = percentage("0.5");

        // when
        Percentage result = p1.div(p2);

        // then
        assertEquals(new BigDecimal("2"), result.value());
    }

    @Test
    void div_by_zero_throws() {
        // given
        Percentage p1 = percentage("1.0");
        Percentage p2 = Percentage.ZERO;

        // when
        assertThrows(ArithmeticException.class, () -> p1.div(p2));
    }

    @Test
    void neg_positive_percentage() {
        // given
        Percentage p = percentage("50%");

        // when
        Percentage result = p.neg();

        // then
        assertEquals(new BigDecimal("-0.5"), result.value());
    }

    @Test
    void neg_negative_percentage() {
        // given
        Percentage p = percentage("-50%");

        // when
        Percentage result = p.neg();

        // then
        assertEquals(new BigDecimal("0.5"), result.value());
    }

    @Test
    void compareTo_equal_percentages() {
        // given
        Percentage p1 = percentage("50%");
        Percentage p2 = percentage("0.5");

        // when
        int result = p1.compareTo(p2);

        // then
        assertEquals(0, result);
    }

    @Test
    void compareTo_smaller_percentage() {
        // given
        Percentage p1 = percentage("10%");
        Percentage p2 = percentage("20%");

        // when
        int result = p1.compareTo(p2);

        // then
        assertTrue(result < 0);
    }

    @Test
    void compareTo_larger_percentage() {
        // given
        Percentage p1 = percentage("30%");
        Percentage p2 = percentage("20%");

        // when
        int result = p1.compareTo(p2);

        // then
        assertTrue(result > 0);
    }

    @Test
    void equals_same_reference() {
        // given
        Percentage p = aRandomPercentage();

        // when
        boolean result = p.equals(p);

        // then
        assertTrue(result);
    }

    @Test
    void equals_same_value() {
        // given
        Percentage p1 = percentage("50%");
        Percentage p2 = percentage("0.5");

        // when
        boolean result = p1.equals(p2);

        // then
        assertTrue(result);
    }

    @Test
    void equals_different_value() {
        // given
        Percentage p1 = percentage("50%");
        Percentage p2 = percentage("60%");

        // when
        boolean result = p1.equals(p2);

        // then
        assertFalse(result);
    }

    @Test
    void equals_different_type() {
        // given
        Percentage p1 = percentage("50%");
        Object p2 = new Object();

        // when
        boolean result = p1.equals(p2);

        // then
        assertFalse(result);
    }

    @Test
    void equals_null() {
        // given
        Percentage p1 = aRandomPercentage();
        Percentage p2 = null;

        // when
        boolean result = p1.equals(p2);

        // then
        assertFalse(result);
    }

    @Test
    void hashCode_equal_for_same_value() {
        // given
        Percentage p1 = percentage("50%");
        Percentage p2 = percentage("0.5");

        // when
        int h1 = p1.hashCode();
        int h2 = p2.hashCode();

        // then
        assertEquals(h1, h2);
    }

    @Test
    void hashCode_different_for_different_values() {
        // given
        Percentage p1 = percentage("50%");
        Percentage p2 = percentage("60%");

        // when
        int h1 = p1.hashCode();
        int h2 = p2.hashCode();

        // then
        assertNotEquals(h1, h2);
    }

    @Test
    void toString_whole_number() {
        // given
        Percentage p = percentage("1.0");

        // when
        String result = p.toString();

        // then
        assertEquals("100%", result);
    }

    @Test
    void toString_fractional_number() {
        // given
        Percentage p = percentage("0.5");

        // when
        String result = p.toString();

        // then
        assertEquals("50%", result);
    }

    @Test
    void toString_small_fraction() {
        // given
        Percentage p = percentage("0.001");

        // when
        String result = p.toString();

        // then
        assertEquals("0.1%", result);
    }

    @Test
    void toString_negative_number() {
        // given
        Percentage p = percentage("-0.5");

        // when
        String result = p.toString();

        // then
        assertEquals("-50%", result);
    }

    @Test
    void toString_strips_trailing_zeros() {
        // given
        Percentage p = percentage("1.000");

        // when
        String result = p.toString();

        // then
        assertEquals("100%", result);
    }
}