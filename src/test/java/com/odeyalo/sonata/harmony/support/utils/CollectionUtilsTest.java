package com.odeyalo.sonata.harmony.support.utils;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CollectionUtilsTest {

    @Test
    void shouldReturnFalseIfTheCollectionDoesNotContainNull() {
        boolean result = CollectionUtils.hasNullElement(List.of("i love Miku<3"));

        assertThat(result).isFalse();
    }

    @Test
    void shouldReturnTrueIfElementHasNullElement() {
        Collection<String> strings = prepareCollectionWithNullElement();

        boolean result = CollectionUtils.hasNullElement(strings);

        assertThat(result).isTrue();
    }

    @NotNull
    private static Collection<String> prepareCollectionWithNullElement() {
        ArrayList<String> strings = new ArrayList<>();
        strings.add("I love Miku!");
        strings.add(null);
        return strings;
    }
}