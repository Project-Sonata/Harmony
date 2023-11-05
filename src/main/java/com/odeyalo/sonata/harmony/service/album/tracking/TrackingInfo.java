package com.odeyalo.sonata.harmony.service.album.tracking;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Value
@AllArgsConstructor(staticName = "of")
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TrackingInfo {
    String trackingId;
}
