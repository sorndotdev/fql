package dev.sorn.fql.api;

import org.junit.jupiter.api.Test;
import static dev.sorn.fql.api.Expression.expression;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SuppressWarnings("all")
public class ExpressionTest {
    @Test
    void expression_null_input_throws() {
        // given
        String invalid = null;

        // when
        FQLError e = assertThrows(FQLError.class, () -> expression(invalid));

        // then
        assertEquals("invalid expression: null", e.getMessage());
    }

    @Test
    void expression_blank_input_throws() {
        // given
        String invalid = "\t \n";

        // when
        FQLError e = assertThrows(FQLError.class, () -> expression(invalid));

        // then
        assertEquals("invalid expression: \t \n", e.getMessage());
    }

    @Test
    void expression_valid_input_returns_correct_instance() {
        // given
        String expression = "DATA(\"TSLA\", \"SHARE_PRICE\", LATEST())";

        // when
        Expression fqlExpression = expression(expression);

        // then
        assertEquals(expression, fqlExpression.toString());
    }

    // ---- equals() tests ----

    @Test
    void equals_same_object_returns_true() {
        // given
        Expression expr = expression("DATA(\"NFLX\", \"SUBSCRIBERS\")");

        // when
        boolean equals = expr.equals(expr);

        // then
        assertTrue(equals);
    }

    @Test
    void equals_same_expression_returns_true() {
        // given
        Expression expr1 = expression("DATA(\"AAPL\", \"REVENUE\")");
        Expression expr2 = expression("DATA(\"AAPL\", \"REVENUE\")");

        // when
        boolean equals = expr1.equals(expr2);

        // then
        assertTrue(equals);
    }

    @Test
    void equals_different_expression_returns_false() {
        // given
        Expression expr1 = expression("DATA(\"AAPL\", \"REVENUE\")");
        Expression expr2 = expression("DATA(\"TSLA\", \"REVENUE\")");

        // when
        boolean equals = expr1.equals(expr2);

        // then
        assertFalse(equals);
    }

    @Test
    void equals_different_type_returns_false() {
        // given
        Expression expr = expression("DATA(\"AAPL\", \"REVENUE\")");

        // when
        boolean equals = expr.equals("not an FQLExpression");

        // then
        assertFalse(equals);
    }

    @Test
    void equals_null_returns_false() {
        // given
        Expression expr = expression("DATA(\"AAPL\", \"REVENUE\")");

        // when
        boolean equals = expr.equals(null);

        // then
        assertFalse(equals);
    }

    // ---- hashCode() tests ----

    @Test
    void hashCode_same_expression_returns_same_hash() {
        // given
        Expression expr1 = expression("DATA(\"GOOG\", \"EPS\")");
        Expression expr2 = expression("DATA(\"GOOG\", \"EPS\")");

        // when
        int hash1 = expr1.hashCode();
        int hash2 = expr2.hashCode();

        // then
        assertEquals(hash1, hash2);
    }

    @Test
    void hashCode_different_expression_returns_different_hash() {
        // given
        Expression expr1 = expression("DATA(\"META\", \"REVENUE\")");
        Expression expr2 = expression("DATA(\"META\", \"NET_INCOME\")");

        // when
        int hash1 = expr1.hashCode();
        int hash2 = expr2.hashCode();

        // then
        assertNotEquals(hash1, hash2);
    }
}