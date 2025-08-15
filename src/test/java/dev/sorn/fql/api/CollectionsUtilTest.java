package dev.sorn.fql.api;

import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import org.junit.jupiter.api.Test;
import static dev.sorn.fql.api.CollectionsUtil.immutableList;
import static dev.sorn.fql.api.CollectionsUtil.immutableSet;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CollectionsUtilTest {
    @Test
    void immutableList_returns_empty_list_when_given_null() {
        // given // when
        List<String> list = immutableList((String) null);

        // then
        assertEquals(0, list.size());
    }

    @Test
    void immutableSet_returns_empty_set_when_given_null() {
        // given // when
        Set<String> set = immutableSet((String) null);

        // then
        assertEquals(0, set.size());
    }

    @Test
    void immutableList_returns_empty_list_when_given_nothing() {
        // given // when
        List<String> list = immutableList();

        // then
        assertEquals(0, list.size());
    }

    @Test
    void immutableSet_returns_empty_set_when_given_nothing() {
        // given // when
        Set<String> set = immutableSet();

        // then
        assertEquals(0, set.size());
    }

    @Test
    void immutableList_is_instance_of_list() {
        // given
        List<String> list = immutableList("a", "b", "c");

        // when
        boolean instance = list instanceof List;

        // then
        assertTrue(instance);
    }

    @Test
    void immutableSet_is_instance_of_set() {
        // given
        Set<String> set = immutableSet("a", "b", "c");

        // when
        boolean instance = set instanceof Set;

        // then
        assertTrue(instance);
    }

    @Test
    void immutableList_cannot_be_modified() {
        // given
        List<String> list = immutableList("a", "b", "c");

        // when
        Consumer<List<String>> f = (l) -> l.add("d");

        // then
        assertThrows(UnsupportedOperationException.class, () -> f.accept(list));
    }

    @Test
    void immutableSet_cannot_be_modified() {
        // given
        Set<String> set = immutableSet("a", "b", "c");

        // when
        Consumer<Set<String>> f = (l) -> l.add("d");

        // then
        assertThrows(UnsupportedOperationException.class, () -> f.accept(set));
    }

    @Test
    void immutableList_given_one_element() {
        // given
        String e = "e";

        // when
        List<String> l = immutableList(e);

        // then
        assertEquals(1, l.size());
        assertEquals(e, l.getFirst());
    }

    @Test
    void immutableSet_given_one_element() {
        // given
        String e = "e";

        // when
        Set<String> l = immutableSet(e);

        // then
        assertEquals(1, l.size());
        assertEquals(e, l.stream().findFirst().orElse(null));
    }
}