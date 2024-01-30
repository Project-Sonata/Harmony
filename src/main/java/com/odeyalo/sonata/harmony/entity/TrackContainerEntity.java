package com.odeyalo.sonata.harmony.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Data
@AllArgsConstructor(staticName = "multiple")
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TrackContainerEntity implements Iterable<SimplifiedTrackEntity> {
    @Getter(value = AccessLevel.NONE)
    @Singular
    List<SimplifiedTrackEntity> items;

    public static TrackContainerEntity single(SimplifiedTrackEntity track) {
        Assert.notNull(track, "Track should not be null!");
        return multiple(Collections.singletonList(track));
    }

    public static TrackContainerEntity wrap(SimplifiedTrackEntity... tracks) {
        return wrap(List.of(tracks));
    }

    public static TrackContainerEntity wrap(List<SimplifiedTrackEntity> tracks) {
        return multiple(tracks);
    }

    public static TrackContainerEntity empty() {
        return builder().build();
    }

    public int size() {
        return items.size();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public boolean contains(TrackEntity o) {
        return items.contains(o);
    }

    public SimplifiedTrackEntity get(int index) {
        return items.get(index);
    }

    @Nullable
    public SimplifiedTrackEntity findById(Long targetId) {
        return items.stream().filter(item -> Objects.equals(item.getId(), targetId)).findFirst()
                .orElse(null);
    }

    @NotNull
    @Override
    public Iterator<SimplifiedTrackEntity> iterator() {
        return items.iterator();
    }

    public Stream<SimplifiedTrackEntity> stream() {
        return items.stream();
    }
}
