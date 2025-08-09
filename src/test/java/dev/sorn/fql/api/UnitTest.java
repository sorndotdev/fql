package dev.sorn.fql.api;

import dev.sorn.fql.UnitTestData;
import org.junit.jupiter.api.Test;
import static dev.sorn.fql.api.Unit.unit;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UnitTest implements UnitTestData {
    @Test
    void unit_value() {
        Unit unit = unit("USD");
        assertEquals("USD", unit.value());
    }

    @Test
    void unit_random_value() {
        Unit unit = aRandomUnit();
        assertNotNull(unit.value());
    }

    @Test
    void unit_is_interface() {
        assertTrue(Unit.class.isInterface(), "Unit should be an interface");
    }

    @Test
    void unit_functional_interface_annotation() {
        boolean isAnnotated = Unit.class.isAnnotationPresent(FunctionalInterface.class);
        assertTrue(isAnnotated, "Unit should be annotated with @FunctionalInterface");
    }

    @Test
    void unit_implements_value_object() {
        Unit unit = unit("USD");
        assertTrue(unit instanceof ValueObject);
    }
}