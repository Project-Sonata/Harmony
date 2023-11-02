package com.odeyalo.sonata.harmony.service.album;

import lombok.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;


@Value
@AllArgsConstructor(staticName = "of")
@Builder
public class TrackUploadTargetContainer implements Iterable<TrackUploadTarget> {
    @Singular
    @Getter(value = AccessLevel.NONE)
    List<TrackUploadTarget> items;

    public static TrackUploadTargetContainer single(@NotNull TrackUploadTarget item) {
        return builder().item(item).build();
    }

    public static TrackUploadTargetContainer fromCollection(@NotNull Collection<TrackUploadTarget> items) {
        Assert.noNullElements(items, "Items should not contain null element!");
        return builder().items(items).build();
    }

    public int size() {
        return items.size();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public boolean contains(TrackUploadTarget o) {
        return items.contains(o);
    }

    public TrackUploadTarget get(int index) {
        return items.get(index);
    }

    public Stream<TrackUploadTarget> stream() {
        return items.stream();
    }

    @NotNull
    @Override
    public Iterator<TrackUploadTarget> iterator() {
        return items.iterator();
    }
}
