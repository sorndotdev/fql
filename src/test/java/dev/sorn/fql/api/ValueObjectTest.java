package dev.sorn.fql.api;

import java.lang.reflect.TypeVariable;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ValueObjectTest {
    @Test
    void returns_string_value() {
        // given
        String s = "<STRING>";

        // when
        ValueObject<String> vo = () -> s;

        // then
        assertEquals(s, vo.value());
    }

    @Test
    void returns_int_value() {
        // given
        int i = 42;

        // when
        ValueObject<Integer> vo = () -> i;

        // then
        assertEquals(i, vo.value());
    }

    @Test
    void functional_interface_annotation() {
        boolean isAnnotated = ValueObject.class.isAnnotationPresent(FunctionalInterface.class);
        assertTrue(isAnnotated, "ValueObject should be annotated with @FunctionalInterface");
    }

    @Test
    void is_interface() {
        assertTrue(ValueObject.class.isInterface(), "ValueObject should be an interface");
    }

    @Test
    void is_parameterized() {
        TypeVariable<?>[] typeParameters = ValueObject.class.getTypeParameters();
        assertEquals(1, typeParameters.length, "ValueObject should have exactly one type parameter");
    }
}