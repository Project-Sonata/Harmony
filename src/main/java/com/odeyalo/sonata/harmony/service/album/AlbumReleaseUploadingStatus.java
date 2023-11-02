package com.odeyalo.sonata.harmony.service.album;

import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * Represent the current status of uploading the album release
 */
@Value
@AllArgsConstructor(staticName = "of")
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AlbumReleaseUploadingStatus {
    Type type;

    public enum Type {
        RECEIVED,
        VALIDATION,
        IMAGE_UPLOAD
    }
}
