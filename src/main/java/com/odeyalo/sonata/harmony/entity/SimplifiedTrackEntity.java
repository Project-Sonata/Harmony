package com.odeyalo.sonata.harmony.entity;

import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * Simplified version of Track entity that does not contain album
 */
@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table("tracks")
public class SimplifiedTrackEntity implements ArtistContainerHolder {
    @Id
    Long id;
    String name;
    @Column("duration_ms")
    Long durationMs;
    @Column("is_explicit")
    boolean explicit;
    @Column("has_lyrics")
    boolean hasLyrics;
    @Column("disc_number")
    Integer discNumber;
    @Column("index")
    Integer index;
    @Column("track_url")
    String trackUrl;
    @Column("album_id")
    Long albumId;
    @Transient
    ArtistContainerEntity artists;

    public boolean hasLyrics() {
        return hasLyrics;
    }
}
