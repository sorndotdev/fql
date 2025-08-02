package dev.sorn.fql.api;

/**
 * Main entry point for using the Financial Query Language (FQL) library.
 *
 * @author Sorn
 */
public final class FQL {
    private FQL() {
        // Prevent direct instantiation
    }

    /**
     * Creates and returns a new instance of {@link FQL} to interact with the library.
     *
     * @return a new {@link FQL} instance
     */
    public static FQL fql() {
        return new FQL();
    }
}