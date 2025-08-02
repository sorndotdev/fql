package dev.sorn.fql.api;

import static java.lang.String.format;

public final class FQLError extends RuntimeException {
    public FQLError(String message, Object... args) {
        super(format(message, args));
    }
}