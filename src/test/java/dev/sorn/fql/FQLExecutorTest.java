package dev.sorn.fql;

import dev.sorn.fql.api.FQLResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static dev.sorn.fql.FQLExecutor.fqlExecutor;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class FQLExecutorTest {
    private FQLExecutor executor;

    @BeforeEach
    void setUp() {
        executor = fqlExecutor();
    }

    @Test
    void executeShouldReturnResult() {
        FQLResult result = executor.execute();
        assertNotNull(result, "result is null");
    }
}