package dev.sorn.fql.api;


import java.util.regex.Pattern;

/**
 * @author Sorn
 */
public final class Checks {
    private Checks() {
        // prevent direct instantiation
    }

    public static <T> T checkPresent(String name, T value) {
        if (value == null) {
            throw new FQLError("'%s' is required", name);
        }
        return value;
    }

    public static int checkMin(String name, int min, int value) {
        if (value < min) {
            throw new FQLError("'%s' is below min: %d < %d", name, value, min);
        }
        return value;
    }

    public static int checkMax(String name, int max, int value) {
        if (value > max) {
            throw new FQLError("'%s' is above max: %d > %d", name, value, max);
        }
        return value;
    }

    public static String checkPattern(String name, Pattern pattern, String value) {
        if (!pattern.matcher(value).matches()) {
            throw new FQLError("'%s' does not match '%s': %s", name, pattern.pattern(), value);
        }
        return value;
    }
}