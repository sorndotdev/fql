package dev.sorn.fql;

import dev.sorn.fql.api.Scale;
import java.util.concurrent.ThreadLocalRandom;

public interface ScaleTestData {
    default Scale aRandomScale() {
        int[] scales = {1, 1_000, 10_000, 1_000_000};
        int chosen = scales[ThreadLocalRandom.current().nextInt(scales.length)];
        return () -> chosen;
    }
}