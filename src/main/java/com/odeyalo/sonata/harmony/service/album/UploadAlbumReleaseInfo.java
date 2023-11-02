package com.odeyalo.sonata.harmony.service.album;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@AllArgsConstructor(staticName = "of")
@Builder
public class UploadAlbumReleaseInfo {
    String albumName;
    Integer totalTracksCount;
}
