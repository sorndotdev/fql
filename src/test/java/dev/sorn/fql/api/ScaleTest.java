package dev.sorn.fql.api;

import dev.sorn.fql.ScaleTestData;
import io.cucumber.core.internal.com.fasterxml.jackson.core.json.WriterBasedJsonGenerator;
import java.util.function.Function;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static dev.sorn.fql.api.Scale.BASE;
import static dev.sorn.fql.api.Scale.scale;
import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ScaleTest implements ScaleTestData {
    @Test
    void scale_value() {
        Scale scale = scale(1_000);
        assertEquals(1_000, scale.value());
    }

    @Test
    void scale_random_value() {
        Scale scale = aRandomScale();
        assertNotNull(scale.value());
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0})
    void scale_invalid_int_below_min_throws(int value) {
        // given
        Function<Integer, Scale> f = Scale::scale;

        // when
        var e = assertThrows(FQLError.class, () -> f.apply(value));

        // then
        assertEquals(format("'scale.value' is below min: %d < %d", value, BASE), e.getMessage());
    }

    @Test
    void scale_is_interface() {
        assertTrue(Scale.class.isInterface(), "Scale should be an interface");
    }

    @Test
    void scale_functional_interface_annotation() {
        boolean isAnnotated = Scale.class.isAnnotationPresent(FunctionalInterface.class);
        assertTrue(isAnnotated, "Scale should be annotated with @FunctionalInterface");
    }

    @Test
    void scale_implements_value_object() {
        Scale scale = scale(1_000_000);
        assertTrue(scale instanceof ValueObject);
    }
}