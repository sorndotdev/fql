package dev.sorn.fql;

import dev.sorn.fql.api.FiscalPeriod;
import java.util.Optional;
import static dev.sorn.fql.api.FiscalPeriod.fiscalPeriod;
import static dev.sorn.fql.api.Optionality.empty;
import static dev.sorn.fql.api.Optionality.optional;

public interface FiscalPeriodTestData extends FiscalYearTestData, FiscalQuarterTestData {
    default FiscalPeriod aFiscalPeriod(String value) {
        return fiscalPeriod(value);
    }

    default FiscalPeriod aRandomFiscalPeriod() {
        return fiscalPeriod(
            aRandomFiscalYear(),
            Math.random() < 0.5
                ? optional(aRandomFiscalQuarter())
                : empty()
        );
    }
}