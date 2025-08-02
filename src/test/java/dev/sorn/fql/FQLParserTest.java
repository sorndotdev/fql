package dev.sorn.fql;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class FQLParserTest {
    @Test
    void testFinancialQueryParserInstance() {
        assertNotNull(new FQLParser());
    }
}