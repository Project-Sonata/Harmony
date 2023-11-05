package com.odeyalo.sonata.harmony.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table("album_release_upload_tracking")
public class AlbumReleaseTrackingEntity {
    @Id
    Long id;
    @Column("tracking_id")
    String trackingId;
    @Column("album_id")
    Long albumId;

    public static AlbumReleaseTrackingEntity of(String trackingId, Long albumId) {
        return builder().trackingId(trackingId).albumId(albumId).build();
    }
}
