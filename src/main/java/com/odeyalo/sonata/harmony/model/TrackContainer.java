package com.odeyalo.sonata.harmony.model;

import lombok.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Value
@Builder
public class TrackContainer implements Iterable<Track> {
    @Singular
    @Getter(value = AccessLevel.NONE)
    List<Track> items;

    public static TrackContainer single(@NotNull Track item) {
        return builder().item(item).build();
    }

    public static TrackContainer fromCollection(@NotNull Collection<Track> items) {
        Assert.noNullElements(items, "Collection should not contain null elements!");
        return builder().items(items).build();
    }

    public Optional<Track> findByTrackName(@NotNull String trackName) {
        return items.stream().filter(track -> track.getTrackName().equals(trackName)).findFirst();
    }

    public int size() {
        return items.size();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public boolean contains(Track o) {
        return items.contains(o);
    }

    public Track get(int index) {
        return items.get(index);
    }

    public Stream<Track> stream() {
        return items.stream();
    }

    @NotNull
    @Override
    public Iterator<Track> iterator() {
        return items.iterator();
    }
}
