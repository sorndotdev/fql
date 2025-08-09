package dev.sorn.fql.api;

@FunctionalInterface
public interface ValueObject<T> {
    T value();
}