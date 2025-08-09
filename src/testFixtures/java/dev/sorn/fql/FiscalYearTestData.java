package dev.sorn.fql;

import dev.sorn.fql.api.FiscalYear;
import static dev.sorn.fql.api.FiscalYear.fiscalYear;

public interface FiscalYearTestData {
    default FiscalYear aFiscalYear(int value) {
        return fiscalYear(value);
    }

    default FiscalYear aRandomFiscalYear() {
        int year = 1000 + (int) (Math.random() * 9000);
        return fiscalYear(year);
    }
}