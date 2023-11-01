package com.odeyalo.sonata.harmony.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table("album_cover_images")
public class AlbumCoverImageEntity {
    @Id
    Long id;
    @NotNull
    @Column("url")
    String url;
    @Column("width")
    Integer width;
    @Column("height")
    Integer height;
    @Column("album_id")
    Long albumId;
}
