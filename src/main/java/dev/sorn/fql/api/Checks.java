package dev.sorn.fql.api;


import java.util.Objects;
import java.util.regex.Pattern;

/**
 * @author Sorn
 */
public final class Checks {
    private Checks() {
        // prevent direct instantiation
    }

    public static <T> T checkNotNull(String name, T value) {
        if (value == null) {
            String caller = CallerInfo.getCallerClassAndMethodName();
            throw new FQLError("[%s]: '%s' is required", caller, name);
        }
        return value;
    }

    public static <T extends Comparable<T>> T checkMin(T min, T value) {
        if (value.compareTo(min) < 0) {
            String caller = CallerInfo.getCallerClassAndMethodName();
            throw new FQLError("[%s] '%s' is below min '%s'", caller, value, min);
        }
        return value;
    }

    public static <T extends Comparable<T>> T checkMax(T max, T value) {
        if (value.compareTo(max) > 0) {
            String caller = CallerInfo.getCallerClassAndMethodName();
            throw new FQLError("[%s] '%s' is above max '%s'", caller, value, max);
        }
        return value;
    }

    public static String checkMatches(Pattern pattern, String value) {
        if (!pattern.matcher(value).matches()) {
            String caller = CallerInfo.getCallerClassAndMethodName();
            throw new FQLError("[%s] '%s' does not match %s", caller, value, pattern.pattern());
        }
        return value;
    }

    public static <T> T checkInstanceOf(Class<T> clazz, Object value) {
        if (!clazz.isAssignableFrom(value.getClass())) {
            String caller = CallerInfo.getCallerClassAndMethodName();
            throw new FQLError("[%s] '%s' is not an instance of '%s'", caller, value, clazz.getName());
        }
        return clazz.cast(value);
    }

    public static <T> void checkEquals(T a, T b) {
        if (!Objects.equals(a, b)) {
            String caller = CallerInfo.getCallerClassAndMethodName();
            throw new FQLError("[%s] '%s' is not equal to '%s'", caller, a, b);
        }
    }
}