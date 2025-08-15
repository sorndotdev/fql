package dev.sorn.fql.api;

import java.util.List;
import java.util.Set;

public final class CollectionsUtil {
    private CollectionsUtil() {
        // prevent direct instantiation
    }

    @SafeVarargs
    public static <E> List<E> immutableList(E... elements) {
        if (elements.length == 0) {
            return List.of();
        }
        if (elements.length == 1 && elements[0] == null) {
            return List.of();
        }
        return List.of(elements);
    }

    @SafeVarargs
    public static <E> Set<E> immutableSet(E... elements) {
        if (elements.length == 0) {
            return Set.of();
        }
        if (elements.length == 1 && elements[0] == null) {
            return Set.of();
        }
        return Set.of(elements);
    }
}