package dev.sorn.fql.api;

/**
 * Value object representing an FQL expression.
 *
 * @author Sorn
 */
public final class FQLExpression {
    private final String value;

    private FQLExpression(String value) {
        this.value = value;
    }

    /**
     * Creates and returns a new instance of {@link FQLResult}.
     *
     * @param expression the raw FQL expression string
     * @return a new {@link FQLResult} instance
     * @throws FQLError if {@code expression} is invalid
     */
    public static FQLExpression fqlExpression(String expression) throws FQLError {
        if (expression == null || expression.isBlank()) {
            throw new FQLError("invalid expression: %s", expression);
        }
        return new FQLExpression(expression);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof FQLExpression that)) {
            return false;
        }
        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}