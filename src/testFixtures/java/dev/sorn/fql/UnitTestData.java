package dev.sorn.fql;

import dev.sorn.fql.api.Unit;
import java.util.concurrent.ThreadLocalRandom;

public interface UnitTestData {
    default Unit aRandomUnit() {
        String[] units = {"USD", "EUR", "GBP", "UNITLESS"};
        String chosen = units[ThreadLocalRandom.current().nextInt(units.length)];
        return () -> chosen;
    }
}