package com.odeyalo.sonata.harmony.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TrackDto {
    String trackName;
    long durationMs;
    boolean isExplicit;
    boolean hasLyrics;
    int discNumber;
    int index;
    ReleaseArtistContainerDto artists;
    String fileId;

    public boolean hasLyrics() {
        return hasLyrics;
    }
}
