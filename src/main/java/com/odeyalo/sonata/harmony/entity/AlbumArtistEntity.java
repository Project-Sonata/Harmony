package com.odeyalo.sonata.harmony.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table("album_artists")
public class AlbumArtistEntity {
    @Column("album_id")
    Long albumId;
    @Column("artist_id")
    Long artistId;

    public static AlbumArtistEntity pairOf(Long albumId, Long artistId) {
        return new AlbumArtistEntity(albumId, artistId);
    }
}
