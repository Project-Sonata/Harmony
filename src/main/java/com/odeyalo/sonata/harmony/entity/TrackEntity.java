package com.odeyalo.sonata.harmony.entity;

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
@Table("tracks")
public class TrackEntity {
    @Id
    Long id;
    String name;
    @Column("duration_ms")
    Long durationMs;
}
