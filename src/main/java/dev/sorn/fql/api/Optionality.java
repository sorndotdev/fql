package dev.sorn.fql.api;

import java.util.Comparator;
import java.util.Optional;

public final class Optionality {
    private Optionality() {
        // prevent direct instantiation
    }

    public static <T> Optional<T> optional(T value) {
        return Optional.ofNullable(value);
    }

    public static <T> Optional<T> empty() {
        return Optional.empty();
    }

    public static <T extends Comparable<? super T>> Comparator<Optional<T>> optionalComparator() {
        return Comparator.comparing(
            o -> o.orElse(null),
            (a, b) -> {
                if (a == null || b == null) return 0;
                return a.compareTo(b);
            }
        );
    }
}