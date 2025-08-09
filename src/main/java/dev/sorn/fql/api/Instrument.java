package dev.sorn.fql.api;

@FunctionalInterface
public interface Instrument extends ValueObject<String> {
    static Instrument instrument(String value) {
        return () -> value;
    }
}