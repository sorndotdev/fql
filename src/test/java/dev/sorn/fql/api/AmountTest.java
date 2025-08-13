package dev.sorn.fql.api;

import dev.sorn.fql.AmountTestData;
import java.math.BigDecimal;
import java.util.function.BiFunction;
import org.junit.jupiter.api.Test;
import static dev.sorn.fql.api.Amount.amount;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AmountTest implements AmountTestData {
    @Test
    void constructor_null_bigdecimal_throws() {
        // given
        BigDecimal value = null;

        // when
        Exception e = assertThrows(FQLError.class, () -> amount(value, ITEMS));

        // then
        assertEquals("[Amount#<init>]: 'value' is required", e.getMessage());
    }

    @Test
    void constructor_null_unit_throws() {
        // given
        Unit nullUnit = null;

        // when
        Exception e = assertThrows(FQLError.class, () -> amount("13", nullUnit));

        // then
        assertEquals("[Amount#<init>]: 'unit' is required", e.getMessage());
    }

    @Test
    void amount_factory_bigdecimal() {
        // given
        BigDecimal value = new BigDecimal("42");

        // when
        Amount amt = amount(value, ITEMS);

        // then
        assertEquals("42 pcs", amt.toString());
    }

    @Test
    void amount_factory_string_null_value_throws() {
        // given
        String nullValue = null;

        // when
        Exception e = assertThrows(FQLError.class, () -> amount(nullValue, ITEMS));

        // then
        assertEquals("[Amount#amount]: 'value' is required", e.getMessage());
    }

    @Test
    void add_same_units() {
        // given
        Amount first = amount("28", ITEMS);
        Amount second = amount("14", ITEMS);

        // when
        Amount result = first.add(second);

        // then
        assertEquals("42 pcs", result.toString());
    }

    @Test
    void add_different_units_throws_exception() {
        // given
        Amount amount1 = amount("13", ITEMS);
        Amount amount2 = amount("13", KILOGRAMS);

        // when
        BiFunction<Amount, Amount, Amount> sum = Amount::add;

        // then
        FQLError e = assertThrows(FQLError.class, () -> sum.apply(amount1, amount2));
        assertEquals("[Amount#checkAmount] 'PCS' is not equal to 'KGS'", e.getMessage());
    }

    @Test
    void sub_same_units() {
        // given
        Amount first = amount("42", ITEMS);
        Amount second = amount("28", ITEMS);

        // when
        Amount result = first.sub(second);

        // then
        assertEquals("14 pcs", result.toString());
    }

    @Test
    void mul_same_units() {
        // given
        Amount first = amount("14", ITEMS);
        Amount second = amount("3", ITEMS);

        // when
        Amount result = first.mul(second);

        // then
        assertEquals("42 pcs", result.toString());
    }

    @Test
    void div_same_units() {
        // given
        Amount first = amount("28", ITEMS);
        Amount second = amount("4", ITEMS);

        // when
        Amount result = first.div(second);

        // then
        assertEquals("7 pcs", result.toString());
    }

    @Test
    void neg_positive() {
        // given
        Amount amount = amount("42", ITEMS);

        // when
        Amount result = amount.neg();

        // then
        assertEquals("-42 pcs", result.toString());
    }

    @Test
    void neg_negative() {
        // given
        Amount amount = amount("-42", ITEMS);

        // when
        Amount result = amount.neg();

        // then
        assertEquals("42 pcs", result.toString());
    }

    @Test
    void abs_positive() {
        // given
        Amount amount = amount("42", ITEMS);

        // when
        DataPointValue result = amount.abs();

        // then
        assertEquals("42 pcs", result.toString());
    }

    @Test
    void abs_negative() {
        // given
        Amount amount = amount("-42", ITEMS);

        // when
        DataPointValue result = amount.abs();

        // then
        assertEquals("42 pcs", result.toString());
    }

    @Test
    void zero() {
        // given
        Amount amount = amount("42", ITEMS);

        // when
        Amount result = amount.zero();

        // then
        assertEquals("0 pcs", result.toString());
    }

    @Test
    void eq_same_value_and_unit() {
        // given
        Amount a1 = amount("42", ITEMS);
        Amount a2 = amount("42", ITEMS);

        // when
        boolean result = a1.eq(a2);

        // then
        assertTrue(result);
    }

    @Test
    void gt_returns_true_when_greater() {
        // given
        Amount bigger = amount("42", ITEMS);
        Amount smaller = amount("28", ITEMS);

        // when
        boolean result = bigger.gt(smaller);

        // then
        assertTrue(result);
    }

    @Test
    void lt_returns_true_when_less() {
        // given
        Amount smaller = amount("28", ITEMS);
        Amount bigger = amount("42", ITEMS);

        // when
        boolean result = smaller.lt(bigger);

        // then
        assertTrue(result);
    }

    @Test
    void gte_true_for_equal_or_greater() {
        // given
        Amount equal = amount("28", ITEMS);
        Amount bigger = amount("42", ITEMS);

        // when
        boolean r1 = equal.gte(equal);
        boolean r2 = bigger.gte(equal);

        // then
        assertTrue(r1);
        assertTrue(r2);
    }

    @Test
    void lte_true_for_equal_or_less() {
        // given
        Amount equal = amount("42", ITEMS);
        Amount smaller = amount("28", ITEMS);

        // when
        boolean r1 = equal.lte(equal);
        boolean r2 = smaller.lte(equal);

        // then
        assertTrue(r1);
        assertTrue(r2);
    }

    @Test
    void is_positive_true_when_value_greater_than_zero() {
        // given
        Amount amt = amount("13", ITEMS);

        // when
        boolean result = amt.isPositive();

        // then
        assertTrue(result);
    }

    @Test
    void is_negative_true_when_value_less_than_zero() {
        // given
        Amount amt = amount("-13", ITEMS);

        // when
        boolean result = amt.isNegative();

        // then
        assertTrue(result);
    }

    @Test
    void is_zero_true_when_value_equals_zero() {
        // given
        Amount amt = amount("0", ITEMS);

        // when
        boolean result = amt.isZero();

        // then
        assertTrue(result);
    }

    @Test
    void getters_return_expected_values() {
        // given
        Amount amt = amount("42", ITEMS);

        // when
        BigDecimal r1 = amt.value();
        Unit r2 = amt.unit();

        // then
        assertEquals(new BigDecimal("42"), r1);
        assertEquals(ITEMS, r2);
    }

    @Test
    void compareTo_equal() {
        // given
        Amount amount1 = amount("42", ITEMS);
        Amount amount2 = amount("42", ITEMS);

        // when
        int result = amount1.compareTo(amount2);

        // then
        assertEquals(0, result);
    }

    @Test
    void compareTo_greater() {
        // given
        Amount amount1 = amount("42", ITEMS);
        Amount amount2 = amount("28", ITEMS);

        // when
        int result = amount1.compareTo(amount2);

        // then
        assertTrue(result > 0);
    }

    @Test
    void compareTo_less() {
        // given
        Amount amount1 = amount("28", ITEMS);
        Amount amount2 = amount("42", ITEMS);

        // when
        int result = amount1.compareTo(amount2);

        // then
        assertTrue(result < 0);
    }

    @Test
    void equals_same_instance() {
        // given
        Amount amount = amount("42", ITEMS);

        // when
        boolean result = amount.equals(amount);

        // then
        assertTrue(result);
    }

    @Test
    void equals_same_value() {
        // given
        Amount amount1 = amount("42", ITEMS);
        Amount amount2 = amount("42", ITEMS);

        // when
        boolean result = amount1.equals(amount2);

        // then
        assertTrue(result);
    }

    @Test
    void equals_different_value() {
        // given
        Amount amount1 = amount("28", ITEMS);
        Amount amount2 = amount("42", ITEMS);

        // when
        boolean result = amount1.equals(amount2);

        // then
        assertFalse(result);
    }

    @Test
    void equals_different_unit() {
        // given
        Amount amount1 = amount("42", ITEMS);
        Amount amount2 = amount("42", KILOGRAMS);

        // when
        boolean result = amount1.equals(amount2);

        // then
        assertFalse(result);
    }

    @Test
    void equals_different_type() {
        // given
        Amount amount = amount("42", ITEMS);
        Object other = new Object();

        // when
        boolean result = amount.equals(other);

        // then
        assertFalse(result);
    }

    @Test
    void equals_null() {
        // given
        Amount amount = amount("42", ITEMS);

        // when
        boolean result = amount.equals(null);

        // then
        assertFalse(result);
    }

    @Test
    void hashCode_same_for_equal_objects() {
        // given
        Amount amount1 = amount("42", ITEMS);
        Amount amount2 = amount("42", ITEMS);

        // when
        int hash1 = amount1.hashCode();
        int hash2 = amount2.hashCode();

        // then
        assertEquals(hash1, hash2);
    }

    @Test
    void hashCode_different_for_different_objects() {
        // given
        Amount amount1 = amount("28", ITEMS);
        Amount amount2 = amount("42", ITEMS);

        // when
        int hash1 = amount1.hashCode();
        int hash2 = amount2.hashCode();

        // then
        assertNotEquals(hash1, hash2);
    }
}