package dev.sorn.fql;

import dev.sorn.fql.api.Money;
import java.math.BigDecimal;
import java.util.concurrent.ThreadLocalRandom;
import static dev.sorn.fql.api.Money.money;

public interface MoneyTestData extends CurrencyTestData {
    default Money aRandomMoney() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        double randomValue = random.nextDouble();
        return money(BigDecimal.valueOf(randomValue), aRandomCurrency());
    }
}