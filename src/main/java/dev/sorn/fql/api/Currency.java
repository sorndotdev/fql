package dev.sorn.fql.api;

@FunctionalInterface
public interface Currency extends Unit {
    static Currency currency(String value) {
        return () -> value;
    }

    default String code() {
        return value();
    }

    default String symbol() {
        try {
            return java.util.Currency.getInstance(code()).getSymbol();
        } catch (IllegalArgumentException e) {
            return Unit.super.symbol();
        }
    }

    default int minorUnit() {
        try {
            return java.util.Currency.getInstance(code()).getDefaultFractionDigits();
        } catch (IllegalArgumentException e) {
            return Unit.super.minorUnit();
        }
    }
}