package com.odeyalo.sonata.harmony.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.odeyalo.sonata.harmony.model.AlbumType;
import com.odeyalo.sonata.harmony.model.ReleaseDate;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UploadAlbumReleaseRequest {
    @NotNull
    String albumName;
    @NotNull
    AlbumType albumType;
    @NotNull
    ReleaseDate releaseDate;
    @NotNull
    @JsonUnwrapped
    ReleaseArtistContainerDto performers;
    @NotNull
    TrackContainerDto tracks;
}
