package com.odeyalo.sonata.harmony.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@Value
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ArtistContainer implements Iterable<Artist> {
    @Singular
    @Getter(value = AccessLevel.NONE)
    List<Artist> items;

    public static ArtistContainer solo(@NotNull Artist item) {
        return builder().item(item).build();
    }

    public static ArtistContainer fromCollection(@NotNull Collection<Artist> items) {
        Assert.noNullElements(items, "Collection cannot contain null element!");
        return builder().items(new ArrayList<>(items)).build();
    }

    public int size() {
        return items.size();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public boolean contains(Artist o) {
        return items.contains(o);
    }

    public Artist get(int index) {
        return items.get(index);
    }

    @NotNull
    @Override
    public Iterator<Artist> iterator() {
        return items.iterator();
    }
}
