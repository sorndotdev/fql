package dev.sorn.fql;

import dev.sorn.fql.api.Unit;
import java.util.concurrent.ThreadLocalRandom;

public interface UnitTestData {
    Unit CONTRACTS = aUnit("CNT", "ctr", 0);
    Unit ITEMS = aUnit("PCS", "pcs", 0);
    Unit KILOGRAMS = aUnit("KGS", "kgs", 0);
    Unit SHARES = aUnit("SHR", "sh", 0);

    default Unit aRandomUnit() {
        Unit[] units = {
            CONTRACTS,
            ITEMS,
            KILOGRAMS,
            SHARES,
        };
        return units[ThreadLocalRandom.current().nextInt(units.length)];
    }

    static Unit aUnit(String code, String symbol, int minorUnit) {
        return new Unit() {
            @Override
            public String value() {
                return code;
            }

            @Override
            public int minorUnit() {
                return minorUnit;
            }

            @Override
            public String symbol() {
                return symbol;
            }

            @Override
            public String toString() {
                return code;
            }
        };
    }
}