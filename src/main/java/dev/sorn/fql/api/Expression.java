package dev.sorn.fql.api;

import dev.sorn.fql.generated.antlr.FQLLexer;
import dev.sorn.fql.generated.antlr.FQLParser;
import org.antlr.v4.runtime.BailErrorStrategy;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.misc.ParseCancellationException;

/**
 * Value object representing an FQL expression.
 *
 * @author Sorn
 */
public final class Expression {
    private final String value;

    private Expression(String value) {
        this.value = value;
    }

    /**
     * Creates and returns a new instance of {@link Expression}.
     *
     * @param expression the raw FQL expression string
     * @return a new {@link Expression} instance
     * @throws FQLError if {@code expression} is invalid
     */
    public static Expression expression(String expression) throws FQLError {
        if (expression == null || expression.isBlank()) {
            throw new FQLError("invalid expression: %s", expression);
        }
        try {
            FQLLexer lexer = new FQLLexer(CharStreams.fromString(expression));
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            FQLParser parser = new FQLParser(tokens);
            parser.removeErrorListeners();
            parser.setErrorHandler(new BailErrorStrategy());
            parser.start();
        } catch (ParseCancellationException e) {
            throw new FQLError("invalid expression: %s", expression, e);
        }
        return new Expression(expression);
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
        if (!(obj instanceof Expression that)) {
            return false;
        }
        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}