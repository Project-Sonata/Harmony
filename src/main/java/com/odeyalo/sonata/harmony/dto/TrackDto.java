package com.odeyalo.sonata.harmony.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TrackDto {
    @NotNull
    String trackName;
    boolean isExplicit;
    boolean hasLyrics;
    int discNumber;
    int index;
    @NotNull
    ReleaseArtistContainerDto artists;
    @NotNull
    String fileId;

    public boolean hasLyrics() {
        return hasLyrics;
    }
}
