package com.odeyalo.sonata.harmony.service.album;

import com.odeyalo.sonata.harmony.model.ArtistContainer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@AllArgsConstructor(staticName = "of")
@Builder
public class TrackUploadTarget {
    String trackName;
    boolean isExplicit;
    boolean hasLyrics;
    int discNumber;
    int index;
    ArtistContainer artists;
    String fileId;

    public boolean hasLyrics() {
        return hasLyrics;
    }
}
