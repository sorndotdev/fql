package dev.sorn.fql.api;

@FunctionalInterface
public interface Source extends ValueObject<String> {
    static Source source(String value) {
        return () -> value;
    }
}