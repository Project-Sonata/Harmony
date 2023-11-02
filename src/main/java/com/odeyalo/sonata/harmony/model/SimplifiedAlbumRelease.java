package com.odeyalo.sonata.harmony.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

/**
 * Simplified {@link AlbumRelease} that does not contain tracks
 */
@Value
@AllArgsConstructor(staticName = "of")
@Builder
public class SimplifiedAlbumRelease {
    String id;
    String name;
    Integer totalTracksCount;
    AlbumType albumType;
    ArtistContainer artists;
}
