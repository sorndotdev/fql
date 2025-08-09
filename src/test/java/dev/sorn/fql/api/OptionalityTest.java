package dev.sorn.fql.api;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.Comparator;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import static dev.sorn.fql.api.Optionality.empty;
import static dev.sorn.fql.api.Optionality.optional;
import static dev.sorn.fql.api.Optionality.optionalComparator;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OptionalityTest {
    @Test
    void constructor_is_private() throws Exception {
        // given
        Constructor<Optionality> constructor = Optionality.class.getDeclaredConstructor();

        // when
        constructor.setAccessible(true);
        constructor.newInstance();

        // then
        assertTrue(Modifier.isPrivate(constructor.getModifiers()), "Constructor should be private");
    }

    @Test
    void optional_returns_present_when_value_non_null() {
        // given
        String value = "X";

        // when
        Optional<String> opt = optional(value);

        // then
        assertTrue(opt.isPresent());
        assertEquals("X", opt.get());
    }

    @Test
    void optional_returns_empty_when_value_null() {
        // given
        String value = null;

        // when
        Optional<String> opt = optional(value);

        // then
        assertTrue(opt.isEmpty());
    }

    @Test
    void empty_returns_empty_optional() {
        // given
        // no input to set up

        // when
        Optional<String> opt = empty();

        // then
        assertTrue(opt.isEmpty());
    }

    @Test
    void optionalComparator_returns_zero_when_any_null() {
        // given
        Comparator<Optional<Integer>> comparator = optionalComparator();

        Optional<Integer> opt1 = Optional.ofNullable(null);
        Optional<Integer> opt2 = Optional.ofNullable(5);

        // when
        int result1 = comparator.compare(opt1, opt2);
        int result2 = comparator.compare(opt2, opt1);

        // then
        assertEquals(0, result1);
        assertEquals(0, result2);
    }

    @Test
    void optionalComparator_compares_non_null_values() {
        // given
        Comparator<Optional<Integer>> comparator = optionalComparator();

        Optional<Integer> opt1 = Optional.of(1);
        Optional<Integer> opt2 = Optional.of(2);

        // when
        int result1 = comparator.compare(opt1, opt2);
        int result2 = comparator.compare(opt2, opt1);
        int result3 = comparator.compare(opt1, Optional.of(1));

        // then
        assertTrue(result1 < 0);
        assertTrue(result2 > 0);
        assertEquals(0, result3);
    }
}