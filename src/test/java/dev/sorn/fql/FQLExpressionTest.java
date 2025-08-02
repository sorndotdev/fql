package dev.sorn.fql;

import dev.sorn.fql.api.FQLError;
import org.junit.jupiter.api.Test;
import static dev.sorn.fql.FQLExpression.fqlExpression;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SuppressWarnings("all")
public class FQLExpressionTest {
    @Test
    void fqlExpression_nullInput_throwsFQLError() {
        // given
        String invalid = null;

        // when
        FQLError e = assertThrows(FQLError.class, () -> fqlExpression(invalid));

        // then
        assertEquals("invalid expression: null", e.getMessage());
    }

    @Test
    void fqlExpression_blankInput_throwsFQLError() {
        // given
        String invalid = "\t \n";

        // when
        FQLError e = assertThrows(FQLError.class, () -> fqlExpression(invalid));

        // then
        assertEquals("invalid expression: \t \n", e.getMessage());
    }

    @Test
    void fqlExpression_validInput_returnsCorrectInstance() {
        // given
        String expression = "DATA(\"TSLA\", \"SHARE_PRICE\", LATEST())";

        // when
        FQLExpression fqlExpression = fqlExpression(expression);

        // then
        assertEquals(expression, fqlExpression.toString());
    }

    // ---- equals() tests ----

    @Test
    void equals_sameObject_returnsTrue() {
        // given
        FQLExpression expr = fqlExpression("DATA(\"NFLX\", \"SUBSCRIBERS\")");

        // when
        boolean equals = expr.equals(expr);

        // then
        assertTrue(equals);
    }

    @Test
    void equals_sameExpression_returnsTrue() {
        // given
        FQLExpression expr1 = fqlExpression("DATA(\"AAPL\", \"REVENUE\")");
        FQLExpression expr2 = fqlExpression("DATA(\"AAPL\", \"REVENUE\")");

        // when
        boolean equals = expr1.equals(expr2);

        // then
        assertTrue(equals);
    }

    @Test
    void equals_differentExpression_returnsFalse() {
        // given
        FQLExpression expr1 = fqlExpression("DATA(\"AAPL\", \"REVENUE\")");
        FQLExpression expr2 = fqlExpression("DATA(\"TSLA\", \"REVENUE\")");

        // when
        boolean equals = expr1.equals(expr2);

        // then
        assertFalse(equals);
    }

    @Test
    void equals_differentType_returnsFalse() {
        // given
        FQLExpression expr = fqlExpression("DATA(\"AAPL\", \"REVENUE\")");

        // when
        boolean equals = expr.equals("not an FQLExpression");

        // then
        assertFalse(equals);
    }

    @Test
    void equals_null_returnsFalse() {
        // given
        FQLExpression expr = fqlExpression("DATA(\"AAPL\", \"REVENUE\")");

        // when
        boolean equals = expr.equals(null);

        // then
        assertFalse(equals);
    }

    // ---- hashCode() tests ----

    @Test
    void hashCode_sameExpression_returnsSameHash() {
        // given
        FQLExpression expr1 = fqlExpression("DATA(\"GOOG\", \"EPS\")");
        FQLExpression expr2 = fqlExpression("DATA(\"GOOG\", \"EPS\")");

        // when
        int hash1 = expr1.hashCode();
        int hash2 = expr2.hashCode();

        // then
        assertEquals(hash1, hash2);
    }

    @Test
    void hashCode_differentExpression_returnsDifferentHash() {
        // given
        FQLExpression expr1 = fqlExpression("DATA(\"META\", \"REVENUE\")");
        FQLExpression expr2 = fqlExpression("DATA(\"META\", \"NET_INCOME\")");

        // when
        int hash1 = expr1.hashCode();
        int hash2 = expr2.hashCode();

        // then
        assertNotEquals(hash1, hash2);
    }
}