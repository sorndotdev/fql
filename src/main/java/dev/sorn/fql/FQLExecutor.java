package dev.sorn.fql;

import dev.sorn.fql.api.FQLResult;
import dev.sorn.fql.generated.antlr.FQLBaseListener;
import static dev.sorn.fql.api.FQLResult.fqlResult;

/**
 * Executes financial query expressions.
 *
 * @author Sorn
 */
public final class FQLExecutor extends FQLBaseListener {
    private FQLExecutor() {
        // Prevent direct instantiation
    }

    /**
     * Creates and returns a new instance of {@link FQLExecutor}.
     *
     * @return a new {@link FQLExecutor} instance
     */
    public static FQLExecutor fqlExecutor() {
        return new FQLExecutor();
    }

    public FQLResult execute() {
        return fqlResult();
    }
}