package com.odeyalo.sonata.harmony.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.List;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ReleaseArtistContainerDto implements Iterable<ReleaseArtistDto> {
    @NonNull
    @NotNull
    @Singular
    @JsonUnwrapped
    List<ReleaseArtistDto> artists;

    public static ReleaseArtistContainerDto empty() {
        return builder().build();
    }

    public static ReleaseArtistContainerDto solo(ReleaseArtistDto artist) {
        return multiple(List.of(artist));
    }

    public static ReleaseArtistContainerDto multiple(List<ReleaseArtistDto> artists) {
        return of(artists);
    }

    public static ReleaseArtistContainerDto multiple(ReleaseArtistDto... artists) {
        return multiple(List.of(artists));
    }

    @NotNull
    @Override
    public Iterator<ReleaseArtistDto> iterator() {
        return artists.iterator();
    }
}
