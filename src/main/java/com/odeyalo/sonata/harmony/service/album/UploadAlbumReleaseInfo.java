package com.odeyalo.sonata.harmony.service.album;

import com.odeyalo.sonata.harmony.model.AlbumType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@AllArgsConstructor(staticName = "of")
@Builder
public class UploadAlbumReleaseInfo {
    String albumName;
    Integer totalTracksCount;
    AlbumType albumType;
}
