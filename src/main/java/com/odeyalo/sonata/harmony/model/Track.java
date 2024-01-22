package com.odeyalo.sonata.harmony.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.net.URI;

@Value
@AllArgsConstructor(staticName = "of")
@Builder(toBuilder = true)
public class Track {
    String id;
    String trackName;
    long durationMs;
    boolean isExplicit;
    boolean hasLyrics;
    int discNumber;
    int index;
    URI trackUrl;
    ArtistContainer artists;
    SimplifiedAlbumRelease album;

    public boolean hasLyrics() {
        return hasLyrics;
    }
}
