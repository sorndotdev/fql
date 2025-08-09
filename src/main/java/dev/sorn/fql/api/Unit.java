package dev.sorn.fql.api;

@FunctionalInterface
public interface Unit extends ValueObject<String> {
    static Unit unit(String value) {
        return () -> value;
    }
}