package dev.sorn.fql;

import dev.sorn.fql.api.Currency;
import java.util.concurrent.ThreadLocalRandom;

public interface CurrencyTestData {
    Currency EUR = aCurrency("EUR", 2);
    Currency GBP = aCurrency("GBP", 2);
    Currency USD = aCurrency("USD", 2);
    Currency JPY = aCurrency("JPY", 0);
    Currency CNY = aCurrency("CNY", 2);
    Currency RUB = aCurrency("RUB", 2);
    Currency DKK = aCurrency("DKK", 2);
    Currency ISK = aCurrency("ISK", 0);
    Currency NOK = aCurrency("NOK", 2);
    Currency SEK = aCurrency("SEK", 2);

    default Currency aRandomCurrency() {
        Currency[] currencies = {
            EUR,
            GBP,
            USD,
            JPY,
            CNY,
            RUB,
            DKK,
            ISK,
            NOK,
            SEK,
        };
        return currencies[ThreadLocalRandom.current().nextInt(currencies.length)];
    }

    static Currency aCurrency(String value, int minorUnit) {
        return new Currency() {
            @Override
            public String value() {
                return value;
            }

            @Override
            public int minorUnit() {
                return minorUnit;
            }

            @Override
            public String toString() {
                return value();
            }
        };
    }
}