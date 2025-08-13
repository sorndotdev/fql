package dev.sorn.fql.api;

import dev.sorn.fql.CurrencyTestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static dev.sorn.fql.api.Currency.currency;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CurrencyTest implements CurrencyTestData {
    @Test
    void currency_value() {
        // given
        Currency currency = currency("USD");

        // when
        String code = currency.value();

        // then
        assertEquals("USD", code);
    }

    @Test
    void currency_random_value() {
        // given
        Unit unit = aRandomCurrency();

        // when
        String code = unit.value();

        // then
        assertNotNull(code);
    }

    @ParameterizedTest
    @CsvSource({
        "EUR,€",
        "GBP,£",
        "USD,$",
        "RUB,₽",
        "CNY,¥",
        "JPY,¥",
    })
    void symbol_from_java_currency_instance() {
        // given
        Currency currency = () -> "EUR";

        // when
        String code = currency.value();
        String symbol = currency.symbol();
        int minorUnit = currency.minorUnit();

        // then
        assertEquals("EUR", code);
        assertEquals("€", symbol);
        assertEquals(2, minorUnit);
    }

    @Test
    void unknown_symbol_does_not_throw() {
        // given
        Currency currency = () -> "UNKNOWN";

        // when
        String code = currency.value();
        String symbol = currency.symbol();
        int minorUnit = currency.minorUnit();

        // then
        assertEquals("UNKNOWN", code);
        assertEquals("UNKNOWN", symbol);
        assertEquals(0, minorUnit);
    }

    @Test
    void currency_is_interface() {
        assertTrue(Currency.class.isInterface(), "Currency should be an interface");
    }

    @Test
    void currency_functional_interface_annotation() {
        boolean isAnnotated = Currency.class.isAnnotationPresent(FunctionalInterface.class);
        assertTrue(isAnnotated, "Currency should be annotated with @FunctionalInterface");
    }

    @Test
    void currency_implements_value_object() {
        Currency currency = currency("USD");
        assertTrue(currency instanceof Unit);
    }
}