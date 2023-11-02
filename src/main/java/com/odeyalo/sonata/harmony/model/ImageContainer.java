package com.odeyalo.sonata.harmony.model;

import lombok.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@Value
@AllArgsConstructor(staticName = "of")
@Builder
public class ImageContainer implements Iterable<Image> {
    @Singular
    @Getter(value = AccessLevel.NONE)
    List<Image> items;

    public static ImageContainer one(@NotNull Image item) {
        return builder().item(item).build();
    }

    public static ImageContainer fromCollection(@NotNull Collection<Image> items) {
        Assert.noNullElements(items, "Null values are not allowed");
        return builder().items(items).build();
    }

    public int size() {
        return items.size();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public boolean contains(Image o) {
        return items.contains(o);
    }

    public Image get(int index) {
        return items.get(index);
    }

    @NotNull
    @Override
    public Iterator<Image> iterator() {
        return items.iterator();
    }
}
