package com.odeyalo.sonata.harmony.service.album;

import com.odeyalo.sonata.harmony.model.AlbumType;
import com.odeyalo.sonata.harmony.model.ArtistContainer;
import com.odeyalo.sonata.harmony.model.ReleaseDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@AllArgsConstructor(staticName = "of")
@Builder
public class UploadAlbumReleaseInfo {
    String albumName;
    Integer totalTracksCount;
    ReleaseDate releaseDate;
    AlbumType albumType;
    ArtistContainer artists;
    TrackUploadTargetContainer tracks;
}
