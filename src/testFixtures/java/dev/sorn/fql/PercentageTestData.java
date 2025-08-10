package dev.sorn.fql;

import dev.sorn.fql.api.Percentage;
import java.util.concurrent.ThreadLocalRandom;
import static dev.sorn.fql.api.Checks.checkMin;
import static dev.sorn.fql.api.Percentage.percentage;

public interface PercentageTestData {
    default Percentage aRandomPercentage() {
        double min = Percentage.MIN_VALUE.doubleValue();
        double max = Percentage.MAX_VALUE.doubleValue();
        return aRandomPercentage(min, max);
    }

    default Percentage aRandomPercentage(double min, double max) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        checkMin("aRandomPercentage.min", Percentage.MIN_VALUE.doubleValue(), min);
        checkMin("aRandomPercentage.max", Percentage.MAX_VALUE.doubleValue(), max);
        return percentage(random.nextDouble(min, max));
    }
}