package dev.sorn.fql.api;

import static dev.sorn.fql.api.Checks.checkMin;

@FunctionalInterface
public interface Scale extends ValueObject<Integer> {
    int BASE = 1;

    static Scale scale(int value) {
        checkMin(BASE, value);
        return () -> value;
    }
}