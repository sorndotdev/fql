package dev.sorn.fql.api;

@FunctionalInterface
public interface Metric extends ValueObject<String> {
    static Metric metric(String value) {
        return () -> value;
    }
}