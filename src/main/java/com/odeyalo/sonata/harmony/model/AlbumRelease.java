package com.odeyalo.sonata.harmony.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@AllArgsConstructor(staticName = "of")
@Builder
public class AlbumRelease {
    String name;
    Integer totalTracksCount;
    AlbumType albumType;
    ArtistContainer artists;
}
