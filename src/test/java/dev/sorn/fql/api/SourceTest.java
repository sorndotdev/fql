package dev.sorn.fql.api;

import dev.sorn.fql.SourceTestData;
import org.junit.jupiter.api.Test;
import static dev.sorn.fql.api.Source.source;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SourceTest implements SourceTestData {
    @Test
    void source_value() {
        Source source = source("BLOOMBERG");
        assertEquals("BLOOMBERG", source.value());
    }

    @Test
    void source_random_value() {
        Source source = aRandomSource();
        assertNotNull(source.value());
    }

    @Test
    void source_is_interface() {
        assertTrue(Source.class.isInterface(), "Source should be an interface");
    }

    @Test
    void source_functional_interface_annotation() {
        boolean isAnnotated = Source.class.isAnnotationPresent(FunctionalInterface.class);
        assertTrue(isAnnotated, "Source should be annotated with @FunctionalInterface");
    }

    @Test
    void source_implements_value_object() {
        Source source = source("BLOOMBERG");
        assertTrue(source instanceof ValueObject);
    }
}