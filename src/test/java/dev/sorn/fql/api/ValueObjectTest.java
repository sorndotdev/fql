package dev.sorn.fql.api;

import java.lang.reflect.TypeVariable;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ValueObjectTest {
    @Test
    void value_object_string_value() {
        // given
        String s = "<STRING>";

        // when
        ValueObject<String> vo = () -> s;

        // then
        assertEquals(s, vo.value());
    }

    @Test
    void value_object_int_value() {
        // given
        int i = 42;

        // when
        ValueObject<Integer> vo = () -> i;

        // then
        assertEquals(i, vo.value());
    }

    @Test
    void value_object_is_interface() {
        assertTrue(ValueObject.class.isInterface(), "ValueObject should be an interface");
    }

    @Test
    void functional_interface_annotation() {
        boolean isAnnotated = ValueObject.class.isAnnotationPresent(FunctionalInterface.class);
        assertTrue(isAnnotated, "ValueObject should be annotated with @FunctionalInterface");
    }

    @Test
    void value_object_is_parameterized() {
        TypeVariable<?>[] typeParameters = ValueObject.class.getTypeParameters();
        assertEquals(1, typeParameters.length, "ValueObject should have exactly one type parameter");
    }

    @Test
    void toString_returns_value_when_no_override() {
        // given
        ValueObject<String> vo = () -> "ABC";

        // when
        String result = vo._toString();

        // then
        assertEquals("ABC", result);
    }

    @Test
    void toString_can_be_overridden() {
        // given
        ValueObject<String> vo = new ValueObject<>() {
            @Override
            public String value() {
                return "XYZ";
            }

            @Override
            public String toString() {
                return "<CUSTOM>";
            }
        };

        // when
        String result = vo._toString();

        // then
        assertEquals("<CUSTOM>", result);
    }
}