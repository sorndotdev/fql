package dev.sorn.fql;

import dev.sorn.fql.api.FiscalQuarter;
import static dev.sorn.fql.api.FiscalQuarter.fiscalQuarter;

public interface FiscalQuarterTestData {
    default FiscalQuarter aFiscalQuarter(int value) {
        return fiscalQuarter(value);
    }

    default FiscalQuarter aRandomFiscalQuarter() {
        int quarter = 1 + (int) (Math.random() * 4);
        return fiscalQuarter(quarter);
    }
}