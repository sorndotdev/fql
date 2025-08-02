package dev.sorn.fql.api;

import org.junit.jupiter.api.Test;
import static dev.sorn.fql.api.FQL.fql;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class FQLTest {
    @Test
    void testInstance() {
        assertNotNull(fql());
    }
}