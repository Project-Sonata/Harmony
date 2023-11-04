package com.odeyalo.sonata.harmony.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AlbumReleaseUploadAcceptedResponse {
    String trackingId;
}
