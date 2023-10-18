package com.odeyalo.sonata.harmony.entity;

import com.odeyalo.sonata.harmony.model.AlbumType;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table("album_releases")
public class AlbumReleaseEntity {
    @Id
    Long id;
    @Column("album_name")
    String albumName;
    @Column("album_type")
    AlbumType albumType;
}