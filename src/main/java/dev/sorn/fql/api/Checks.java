package dev.sorn.fql.api;


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
            throw new FQLError("'%s' is required", name);
        }
        return value;
    }

    public static <T extends Comparable<T>> T checkMin(String name, T min, T value) {
        if (value.compareTo(min) < 0) {
            throw new FQLError("'%s' is below min: %s < %s", name, value, min);
        }
        return value;
    }

    public static <T extends Comparable<T>> T checkMax(String name, T max, T value) {
        if (value.compareTo(max) > 0) {
            throw new FQLError("'%s' is above max: %s > %s", name, value, max);
        }
        return value;
    }

    public static String checkMatches(String name, Pattern pattern, String value) {
        if (!pattern.matcher(value).matches()) {
            throw new FQLError("'%s' does not match '%s': %s", name, pattern.pattern(), value);
        }
        return value;
    }
}