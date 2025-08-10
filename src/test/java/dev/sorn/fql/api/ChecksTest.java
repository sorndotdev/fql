package dev.sorn.fql.api;


import java.lang.reflect.Constructor;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.regex.Pattern;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static dev.sorn.fql.api.Checks.checkMatches;
import static dev.sorn.fql.api.Checks.checkMax;
import static dev.sorn.fql.api.Checks.checkMin;
import static java.lang.reflect.Modifier.isPrivate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ChecksTest {
    @Test
    void checkNotNull_returns_object() {
        // given
        String name = "<TEST>";
        Object o1 = new Object();

        // when
        BiFunction<String, Object, Object> f = Checks::checkNotNull;

        // then
        Object o2 = f.apply(name, o1);
        assertEquals(o1, o2);
    }

    @Test
    void checkNotNull_null_throws() {
        // given
        String name = "<TEST>";
        Object o = null;

        // when
        BiFunction<String, Object, Object> f = Checks::checkNotNull;

        // then
        var e = assertThrows(FQLError.class, () -> f.apply(name, o));
        assertEquals("[ChecksTest#lambda$checkNotNull_null_throws$0]: '<TEST>' is required", e.getMessage());
    }

    @ParameterizedTest
    @ValueSource(ints = {1000, 1001})
    void checkMin_value_within_returns_int(int v) {
        // given
        String name = "<TEST>";
        int min = 1000;

        // when
        int v2 = checkMin(min, v);

        // then
        assertEquals(v, v2);
    }

    @Test
    void checkMin_value_below_throws() {
        // given
        String name = "<TEST>";
        int min = 1000;
        int v = 999;

        // when
        BiFunction<Integer, Integer, Integer> f = Checks::checkMin;

        // then
        var e = assertThrows(FQLError.class, () -> f.apply(min, v));
        assertEquals("[ChecksTest#lambda$checkMin_value_below_throws$1] '999' is below min '1000'", e.getMessage());
    }

    @ParameterizedTest
    @ValueSource(ints = {999, 1000})
    void checkMax_value_within_returns_int(int v) {
        // given
        String name = "<TEST>";
        int max = 1000;

        // when
        int v2 = checkMax(max, v);

        // then
        assertEquals(v, v2);
    }

    @Test
    void checkMax_value_above_throws() {
        // given
        int max = 1000;
        int v = 1001;

        // when
        BiFunction<Integer, Integer, Integer> f = Checks::checkMax;

        // then
        var e = assertThrows(FQLError.class, () -> f.apply(max, v));
        assertEquals("[ChecksTest#lambda$checkMax_value_above_throws$2] '1001' is above max '1000'", e.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "aa",
        "aaa",
        "aba",
        "bab",
        "abba",
        "abbba",
        "babab",
    })
    void checkMatches_match_returns(String value) {
        // given
        Pattern pattern = Pattern.compile("^(?i)([a-z])([a-z])?([a-z])?\\2?\\1$");

        // when
        String s = checkMatches(pattern, value);

        // then
        assertEquals(value, s);
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "aab",
        "baa",
        "abc",
        "123",
        "a-b-a",
    })
    void checkMatches_no_match_throws(String value) {
        // given
        Pattern pattern = Pattern.compile("^(?i)([a-z])([a-z])?([a-z])?\\2?\\1$");

        // when
        BiFunction<Pattern, String, String> f = Checks::checkMatches;

        // then
        var e = assertThrows(FQLError.class, () -> f.apply(pattern, value));
        assertEquals(String.format("[ChecksTest#lambda$checkMatches_no_match_throws$3] '%s' does not match ^(?i)([a-z])([a-z])?([a-z])?\\2?\\1$", value), e.getMessage());
    }

    @Test
    void checkInstanceOf_throws() {
        // given
        Double value = 1.0;

        // when
        BiFunction<Class<Integer>, Object, Integer> f = Checks::checkInstanceOf;

        // then
        var e = assertThrows(FQLError.class, () -> f.apply(Integer.class, value));
        assertEquals("[ChecksTest#lambda$checkInstanceOf_throws$4] '1.0' is not an instance of 'java.lang.Integer'", e.getMessage());
    }

    @Test
    void checks_private_constructor() throws Exception {
        Constructor<Checks> constructor = Checks.class.getDeclaredConstructor();
        assertTrue(isPrivate(constructor.getModifiers()), "constructor is not private");
    }
}