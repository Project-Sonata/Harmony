package com.odeyalo.sonata.harmony.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;
import org.springframework.util.Assert;

import java.util.Iterator;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AlbumCoverImageContainerEntity implements Iterable<AlbumCoverImageEntity> {
    @NotNull
    @Singular
    List<AlbumCoverImageEntity> items;

    public static AlbumCoverImageContainerEntity of(List<AlbumCoverImageEntity> items) {
        Assert.notNull(items, "Items of AlbumCoverImageEntity cannot be null!");
        return builder().items(items).build();
    }

    public static AlbumCoverImageContainerEntity single(AlbumCoverImageEntity item) {
        Assert.notNull(item, "Item cannot be null!");
        return builder().item(item).build();
    }

    public static AlbumCoverImageContainerEntity empty() {
        return builder().build();
    }

    public int size() {
        return getItems().size();
    }

    public boolean isEmpty() {
        return getItems().isEmpty();
    }

    public boolean contains(AlbumCoverImageEntity o) {
        return getItems().contains(o);
    }

    public AlbumCoverImageEntity get(int index) {
        return getItems().get(index);
    }

    @NotNull
    @Override
    public Iterator<AlbumCoverImageEntity> iterator() {
        return items.iterator();
    }
}
