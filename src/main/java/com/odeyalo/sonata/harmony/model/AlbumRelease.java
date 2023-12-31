package com.odeyalo.sonata.harmony.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@AllArgsConstructor(staticName = "of")
@Builder
public class AlbumRelease {
    Long id;
    String name;
    Integer totalTracksCount;
    ReleaseDate releaseDate;
    AlbumType albumType;
    ArtistContainer artists;
    TrackContainer tracks;
    ImageContainer images;
}
