package com.odeyalo.sonata.harmony.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Collections.singletonList;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TrackContainerDto implements Iterable<TrackDto> {
    int totalTracksCount;
    @Singular
    List<TrackDto> items;

    public static TrackContainerDto single(TrackDto item) {
        return of(1, singletonList(item));
    }

    public int size() {
        return items.size();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public boolean contains(TrackDto o) {
        return items.contains(o);
    }

    public TrackDto get(int index) {
        return items.get(index);
    }

    public Stream<TrackDto> stream() {
        return items.stream();
    }

    @Override
    @NotNull
    public Iterator<TrackDto> iterator() {
        return items.iterator();
    }
}
