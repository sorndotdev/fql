package dev.sorn.fql;

import dev.sorn.fql.api.FiscalPeriod;
import dev.sorn.fql.api.FiscalPeriodRange;
import static dev.sorn.fql.api.FiscalPeriodRange.fiscalPeriodRange;
import static dev.sorn.fql.api.Optionality.empty;
import static dev.sorn.fql.api.Optionality.optional;

public interface FiscalPeriodRangeTestData extends FiscalPeriodTestData {
    default FiscalPeriodRange aFiscalPeriodRange(String start, String end) {
        return fiscalPeriodRange(
            start != null ? optional(aFiscalPeriod(start)) : empty(),
            end != null ? optional(aFiscalPeriod(end)) : empty()
        );
    }

    default FiscalPeriodRange aRandomFiscalPeriodRange() {
        double selector = Math.random();
        if (selector < 0.25) {
            return aFiscalPeriodRange(null, null);
        } else if (selector < 0.5) {
            return aFiscalPeriodRange(aRandomFiscalPeriod().toString(), null);
        } else if (selector < 0.75) {
            return aFiscalPeriodRange(null, aRandomFiscalPeriod().toString());
        } else {
            FiscalPeriod startPeriod = aRandomFiscalPeriod();
            FiscalPeriod endPeriod = aRandomFiscalPeriod();
            if (startPeriod.compareTo(endPeriod) > 0) {
                FiscalPeriod temp = startPeriod;
                startPeriod = endPeriod;
                endPeriod = temp;
            }
            return aFiscalPeriodRange(startPeriod.toString(), endPeriod.toString());
        }
    }
}