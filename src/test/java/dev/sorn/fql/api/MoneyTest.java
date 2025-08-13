package dev.sorn.fql.api;

import dev.sorn.fql.MoneyTestData;
import java.math.BigDecimal;
import java.util.Locale;
import java.util.function.BiFunction;
import org.junit.jupiter.api.Test;
import static dev.sorn.fql.api.Money.money;
import static java.math.RoundingMode.HALF_EVEN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MoneyTest implements MoneyTestData {
    @Test
    void money_factory_creates_money() {
        // given
        BigDecimal value = new BigDecimal("42.42");

        // when
        Money m = money(value, USD);

        // then
        assertEquals(value.setScale(2, HALF_EVEN), m.value());
        assertEquals(USD, m.unit());
    }

    @Test
    void money_factory_null_amount_throws() {
        // given
        BigDecimal value = null;

        // when
        Exception e = assertThrows(FQLError.class, () -> money(value, USD));

        // then
        assertEquals("[Amount#<init>]: 'value' is required", e.getMessage());
    }

    @Test
    void toString_usd_en_US_positive() {
        // "   4242.42, USD, en-US, $1,234.56",
        // given
        BigDecimal value = new BigDecimal("424242.42");
        Locale locale = Locale.forLanguageTag("en-US");

        // when
        Money money = money(value, USD).withLocale(locale);
        String result = money.toString();

        // then
        assertEquals("$424,242.42", result);
    }

    @Test
    void toString_usd_en_US_negative() {
        // given
        BigDecimal value = new BigDecimal("-424242.42");
        Locale locale = Locale.forLanguageTag("en-US");

        // when
        Money money = money(value, USD).withLocale(locale);
        String result = money.toString();

        // then
        assertEquals("-$424,242.42", result);
    }

    @Test
    void toString_eur_de_DE_positive() {
        // given
        BigDecimal value = new BigDecimal("424242.42");
        Locale locale = Locale.forLanguageTag("de-DE");

        // when
        Money money = money(value, EUR).withLocale(locale);
        String result = money.toString();

        // then
        assertEquals("424.242,42 €", result);
    }

    @Test
    void toString_eur_de_DE_negative() {
        // given
        BigDecimal value = new BigDecimal("-424242.42");
        Locale locale = Locale.forLanguageTag("de-DE");

        // when
        Money money = money(value, EUR).withLocale(locale);
        String result = money.toString();

        // then
        assertEquals("-424.242,42 €", result);
    }

    @Test
    void toString_cny_zh_CN() {
        // given
        BigDecimal value = new BigDecimal("424242.42");
        Locale locale = Locale.forLanguageTag("zh-CN");

        // when
        Money money = money(value, CNY).withLocale(locale);
        String result = money.toString();

        // then
        assertEquals("¥424,242.42", result);
    }

    @Test
    void toString_cny_de_DE() {
        // given
        BigDecimal value = new BigDecimal("424242.42");
        Locale locale = Locale.forLanguageTag("de-DE");

        // when
        Money money = money(value, CNY).withLocale(locale);
        String result = money.toString();

        // then
        assertEquals("424.242,42 CN¥", result);
    }

    @Test
    void toString_cny_en_US() {
        // given
        BigDecimal value = new BigDecimal("424242.42");
        Locale locale = Locale.forLanguageTag("en-US");

        // when
        Money money = money(value, CNY).withLocale(locale);
        String result = money.toString();

        // then
        assertEquals("CN¥424,242.42", result);
    }

    @Test
    void toString_jpy() {
        // given
        BigDecimal value = new BigDecimal("424242.42");
        Locale locale = Locale.forLanguageTag("ja-JP");

        // when
        Money money = money(value, JPY).withLocale(locale);
        String result = money.toString();

        // then
        assertEquals("￥424,242", result);
    }

    @Test
    void with_locale_changes_formatting() {
        // given
        Money money = money(new BigDecimal("1000"), EUR).withLocale(Locale.US);

        // when
        Money result = money.withLocale(Locale.GERMANY);

        // then
        assertEquals("1.000,00 €", result.toString());
    }

    @Test
    void add_same_currency() {
        // given
        Money m1 = money(new BigDecimal("100"), USD);
        Money m2 = money(new BigDecimal("50"), USD);

        // when
        Money sum = m1.add(m2);

        // then
        assertEquals("$150.00", sum.toString());
    }

    @Test
    void add_different_currency_throws() {
        // given
        Money m1 = money(new BigDecimal("100"), USD);
        Money m2 = money(new BigDecimal("50"), EUR);

        // when
        BiFunction<Money, Money, Money> f = Money::add;

        // then
        FQLError e = assertThrows(FQLError.class, () -> f.apply(m1, m2));
        assertEquals("[Amount#checkAmount] 'USD' is not equal to 'EUR'", e.getMessage());
    }

    @Test
    void sub_same_currency() {
        // given
        Money m1 = money(new BigDecimal("100"), USD);
        Money m2 = money(new BigDecimal("50"), USD);

        // when
        Money sum = m1.sub(m2);

        // then
        assertEquals("$50.00", sum.toString());
    }

    @Test
    void mul_same_currency() {
        // given
        Money m1 = money(new BigDecimal("25"), USD);
        Money m2 = money(new BigDecimal("4"), USD);

        // when
        Money product = m1.mul(m2);

        // then
        assertEquals("$100.00", product.toString());
    }

    @Test
    void div_same_currency() {
        // given
        Money m1 = money(new BigDecimal("100"), USD);
        Money m2 = money(new BigDecimal("4"), USD);

        // when
        Money quotient = m1.div(m2);

        // then
        assertEquals("$25.00", quotient.toString());
    }

    @Test
    void neg_positive() {
        // given
        Money money = money(new BigDecimal("100"), USD);

        // when
        Money result = money.neg();

        // then
        assertEquals("-$100.00", result.toString());
    }

    @Test
    void neg_negative() {
        // given
        Money money = money(new BigDecimal("-100"), USD);

        // when
        Money result = money.neg();

        // then
        assertEquals("$100.00", result.toString());
    }

    @Test
    void abs_positive() {
        // given
        Money money = money(new BigDecimal("100"), USD);

        // when
        DataPointValue result = money.abs();

        // then
        assertEquals("$100.00", result.toString());
    }

    @Test
    void abs_negative() {
        // given
        Money money = money(new BigDecimal("-100"), USD);

        // when
        DataPointValue result = money.abs();

        // then
        assertEquals("$100.00", result.toString());
    }

    @Test
    void zero() {
        // given
        Money money = money(new BigDecimal("-100"), USD);

        // when
        DataPointValue result = money.zero();

        // then
        assertEquals("$0.00", result.toString());
    }

    @Test
    void compareTo_equal() {
        // given
        Money money1 = money(new BigDecimal("100"), USD);
        Money money2 = money(new BigDecimal("100"), USD);

        // when
        int result = money1.compareTo(money2);

        // then
        assertEquals(0, result);
    }

    @Test
    void equals_same_instance() {
        // given
        Money money = money(new BigDecimal("100"), USD);

        // when
        boolean result = money.equals(money);

        // then
        assertTrue(result);
    }

    @Test
    void equals_same_value_same_currency() {
        // given
        Money money1 = money(new BigDecimal("100"), USD);
        Money money2 = money(new BigDecimal("100"), USD);

        // when
        boolean result = money1.equals(money2);

        // then
        assertTrue(result);
    }

    @Test
    void equals_different_value() {
        // given
        Money money1 = money(new BigDecimal("100"), USD);
        Money money2 = money(new BigDecimal("200"), USD);

        // when
        boolean eq = money1.equals(money2);

        // then
        assertFalse(eq);
    }

    @Test
    void equals_different_currency() {
        // given
        Money money1 = money(new BigDecimal("100"), USD);
        Money money2 = money(new BigDecimal("100"), EUR);

        // when
        boolean eq = money1.equals(money2);

        // then
        assertFalse(eq);
    }

    @Test
    void equals_different_type() {
        // given
        Money money = money(new BigDecimal("100"), USD);
        Object other = new Object();

        // when
        boolean eq = money.equals(other);

        // then
        assertFalse(eq);
    }

    @Test
    void equals_null() {
        // given
        Money money = money(new BigDecimal("100"), USD);

        // when
        boolean eq = money.equals(null);

        // then
        assertFalse(eq);
    }

    @Test
    void hashCode_same_for_equal_objects() {
        // given
        Money money1 = money(new BigDecimal("100"), USD);
        Money money2 = money(new BigDecimal("100"), USD);

        // when
        int hash1 = money1.hashCode();
        int hash2 = money2.hashCode();

        // then
        assertEquals(hash1, hash2);
    }

    @Test
    void hashCode_different_for_different_objects() {
        // given
        Money money1 = money(new BigDecimal("100"), USD);
        Money money2 = money(new BigDecimal("200"), USD);

        // when
        int hash1 = money1.hashCode();
        int hash2 = money2.hashCode();

        // then
        assertNotEquals(hash1, hash2);
    }

    @Test
    void currency_returns_correct_currency() {
        // given
        Money money = money(new BigDecimal("100"), USD);

        // when
        Unit result = money.unit();

        // then
        assertEquals(USD, result);
    }
}