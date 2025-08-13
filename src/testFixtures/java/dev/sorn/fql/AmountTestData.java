package dev.sorn.fql;

import dev.sorn.fql.api.Amount;
import java.math.BigDecimal;
import java.util.concurrent.ThreadLocalRandom;
import static dev.sorn.fql.api.Amount.amount;

public interface AmountTestData extends UnitTestData {
    default Amount aRandomAmount() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        double randomValue = random.nextDouble();
        return amount(BigDecimal.valueOf(randomValue), aRandomUnit());
    }
}