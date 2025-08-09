package dev.sorn.fql.api;


import java.lang.reflect.Constructor;
import java.util.function.BiFunction;
import java.util.regex.Pattern;
import org.apache.commons.lang3.function.TriFunction;
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

public class ChecksTest {
    @Test
    void checkPresent_returns_object() {
        // given
        String name = "<TEST>";
        Object o1 = new Object();

        // when
        BiFunction<String, Object, Object> f = Checks::checkPresent;

        // then
        Object o2 = f.apply(name, o1);
        assertEquals(o1, o2);
    }

    @Test
    void checkPresent_null_throws() {
        // given
        String name = "<TEST>";
        Object o = null;

        // when
        BiFunction<String, Object, Object> f = Checks::checkPresent;

        // then
        var e = assertThrows(FQLError.class, () -> f.apply(name, o));
        assertEquals("'<TEST>' is required", e.getMessage());
    }

    @ParameterizedTest
    @ValueSource(ints = {1000, 1001})
    void checkMin_value_within_returns_int(int v) {
        // given
        String name = "<TEST>";
        int min = 1000;

        // when
        int v2 = checkMin(name, min, v);

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
        TriFunction<String, Integer, Integer, Integer> f = Checks::checkMin;

        // then
        var e = assertThrows(FQLError.class, () -> f.apply(name, min, v));
        assertEquals("'<TEST>' is below min: 999 < 1000", e.getMessage());
    }

    @ParameterizedTest
    @ValueSource(ints = {999, 1000})
    void checkMax_value_within_returns_int(int v) {
        // given
        String name = "<TEST>";
        int max = 1000;

        // when
        int v2 = checkMax(name, max, v);

        // then
        assertEquals(v, v2);
    }

    @Test
    void checkMax_value_above_throws() {
        // given
        String name = "<TEST>";
        int max = 1000;
        int v = 1001;

        // when
        TriFunction<String, Integer, Integer, Integer> f = Checks::checkMax;

        // then
        var e = assertThrows(FQLError.class, () -> f.apply(name, max, v));
        assertEquals("'<TEST>' is above max: 1001 > 1000", e.getMessage());
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
        String s = checkMatches("palindrome", pattern, value);

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
        TriFunction<String, Pattern, String, String> f = Checks::checkMatches;

        // then
        assertThrows(FQLError.class, () -> f.apply("palindrome", pattern, value));
    }

    @Test
    void checks_private_constructor() throws Exception {
        Constructor<Checks> constructor = Checks.class.getDeclaredConstructor();
        assertTrue(isPrivate(constructor.getModifiers()), "constructor is not private");
    }
}