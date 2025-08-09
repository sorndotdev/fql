package dev.sorn.fql.api;

@FunctionalInterface
public interface ValueObject<T> {
    T value();

    default String _toString() {
        if (isDefaultToString(this)) {
            return String.valueOf(this.value());
        }
        return this.toString();
    }

    private static boolean isDefaultToString(Object obj) {
        try {
            return obj.getClass().getMethod("toString").getDeclaringClass().equals(Object.class);
        } catch (NoSuchMethodException e) {
            return false;
        }
    }
}