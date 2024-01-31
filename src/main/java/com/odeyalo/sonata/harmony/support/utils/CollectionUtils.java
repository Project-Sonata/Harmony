package com.odeyalo.sonata.harmony.support.utils;

import java.util.Collection;
import java.util.HashSet;

public class CollectionUtils {

    public static <T> boolean hasNullElement(Collection<T> items) {
        try {
            return items.contains(null);
        } catch (NullPointerException e) {
            // Collection does not support null values, just return false
            return false;
        }
    }

    public static <T> boolean containsAll(Collection<T> source, Collection<T> target) {
        return new HashSet<>(source).containsAll(target);
    }
}
