package com.odeyalo.sonata.harmony.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.odeyalo.sonata.harmony.model.AlbumType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import jakarta.validation.constraints.NotNull;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UploadAlbumReleaseRequest {
    @NotNull
    String albumName;
    @NotNull
    AlbumType albumType;
    @NotNull
    ReleaseArtistContainerDto performers;
    @NotNull
    TrackContainerDto tracks;
}
