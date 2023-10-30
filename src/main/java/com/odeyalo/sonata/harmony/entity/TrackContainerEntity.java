package com.odeyalo.sonata.harmony.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@Data
@AllArgsConstructor(staticName = "multiple")
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TrackContainerEntity implements Iterable<TrackEntity> {
    @Getter(value = AccessLevel.NONE)
    @Singular
    List<TrackEntity> items;

    public static TrackContainerEntity single(TrackEntity track) {
        Assert.notNull(track, "Track should not be null!");
        return multiple(Collections.singletonList(track));
    }

    public static TrackContainerEntity wrap(List<TrackEntity> tracks) {
        return multiple(tracks);
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

    public TrackEntity get(int index) {
        return items.get(index);
    }

    @NotNull
    @Override
    public Iterator<TrackEntity> iterator() {
        return items.iterator();
    }
}
