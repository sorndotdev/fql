package dev.sorn.fql.api;

/**
 * Financial query execution result.
 *
 * @author Sorn
 */
public final class FQLResult {
    private FQLResult() {
        // Prevent direct instantiation
    }

    /**
     *
     * @return a new {@link FQLResult} instance
     */
    public static FQLResult fqlResult() {
        return new FQLResult();
    }
}