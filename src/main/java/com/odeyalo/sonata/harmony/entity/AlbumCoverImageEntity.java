package com.odeyalo.sonata.harmony.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table("album_cover_images")
public class AlbumCoverImageEntity {
    @NotNull
    @Column("url")
    String url;
    @NotNull
    @Column("width")
    Integer width;
    @NotNull
    @Column("height")
    Integer height;
    @NotNull
    @Column("album_id")
    Long albumId;
}
