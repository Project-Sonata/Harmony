package com.odeyalo.sonata.harmony.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Transient;


@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString(callSuper = true)
public class AlbumReleaseEntity extends SimplifiedAlbumEntity {
    @Transient
    TrackContainerEntity tracks;

}