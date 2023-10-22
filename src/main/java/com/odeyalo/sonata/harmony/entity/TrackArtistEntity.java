package com.odeyalo.sonata.harmony.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * Represent the conjunction table called 'track_artists'.
 * Used for Many-to-Many relationship between track and artist and vice-versa.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table("track_artists")
public class TrackArtistEntity {
    @Column("track_id")
    @NotNull
    Long trackId;
    @Column("artist_id")
    @NotNull
    Long artistId;

    public static TrackArtistEntity pairOf(Long trackId, Long artistId) {
        return new TrackArtistEntity(trackId, artistId);
    }
}
