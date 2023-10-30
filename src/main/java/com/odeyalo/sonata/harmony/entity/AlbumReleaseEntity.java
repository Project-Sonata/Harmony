package com.odeyalo.sonata.harmony.entity;

import com.odeyalo.sonata.harmony.model.AlbumType;
import com.odeyalo.sonata.harmony.model.ReleaseDate;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table("album_releases")
public class AlbumReleaseEntity implements ArtistContainerHolder {
    @Id
    Long id;
    @Column("album_name")
    String albumName;
    @Column("album_type")
    AlbumType albumType;
    @Column("duration_ms")
    Long durationMs;
    @Column("total_tracks_count")
    Integer totalTracksCount;
    @Transient
    ReleaseDate releaseDate;
    @Transient
    ArtistContainerEntity artists;
    @Transient
    TrackContainerEntity tracks;
    // A columns to represent the release date
    // written here because of Spring R2DBC does not support embedded values.
    // It can't be achieved using AfterConvertCallback invocation due to lack of Row data, e.g. the values from database cannot be accessed in callback
    // also, can't do this using Spring Converter<ReleaseDate, OutboundRow>
    @Column("release_date")
    @EqualsAndHashCode.Exclude
    String releaseDateAsString;
    @Column("release_date_precision")
    @EqualsAndHashCode.Exclude
    String releaseDatePrecision;
}