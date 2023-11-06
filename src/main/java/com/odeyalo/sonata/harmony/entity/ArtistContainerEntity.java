package com.odeyalo.sonata.harmony.entity;

import lombok.*;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;

/**
 * Immutable container for artists.
 */
@Value
@AllArgsConstructor(staticName = "multiple")
@Builder
public class ArtistContainerEntity implements Iterable<ArtistEntity> {
    @NotNull
    @NonNull
    @Getter(value = AccessLevel.PRIVATE)
    @Singular
    List<ArtistEntity> artists;

    public static ArtistContainerEntity empty() {
        return builder().artists(emptyList()).build();
    }

    public static ArtistContainerEntity solo(@NotNull ArtistEntity artist) {
        return multiple(List.of(artist));
    }

    public static ArtistContainerEntity multiple(ArtistEntity... artists) {
        return multiple(List.of(artists));
    }

    public int size() {
        return getArtists().size();
    }

    public boolean isEmpty() {
        return getArtists().isEmpty();
    }

    public ArtistEntity get(int index) {
        return getArtists().get(index);
    }

    @NotNull
    @Override
    public Iterator<ArtistEntity> iterator() {
        return artists.iterator();
    }

    public Stream<ArtistEntity> stream() {
        return artists.stream();
    }
}
