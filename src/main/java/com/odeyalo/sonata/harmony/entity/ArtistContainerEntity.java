package com.odeyalo.sonata.harmony.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.List;

/**
 * Immutable container for artists.
 */
@Value
@AllArgsConstructor(staticName = "multiple")
@Builder
public class ArtistContainerEntity implements Iterable<ArtistEntity> {
    @NotNull
    @NonNull
    List<ArtistEntity> artists;

    public static ArtistContainerEntity solo(@NotNull ArtistEntity artist) {
        return multiple(List.of(artist));
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
}
