package com.odeyalo.sonata.harmony.service.album;

import com.odeyalo.sonata.harmony.model.ArtistContainer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.Accessors;

@Value
@AllArgsConstructor(staticName = "of")
@Builder
public class TrackUploadTarget {
    String trackName;
    long durationMs;
    boolean isExplicit;
    @Accessors(fluent = true)
    boolean hasLyrics;
    int discNumber;
    int index;
    ArtistContainer artists;
    String fileId;
}
