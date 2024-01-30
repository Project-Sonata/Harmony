package com.odeyalo.sonata.harmony.repository;

import com.odeyalo.sonata.harmony.entity.AlbumReleaseEntity;
import com.odeyalo.sonata.harmony.model.AlbumRelease;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import reactor.core.publisher.Mono;

/**
 * Operations that can be made to update the {@link AlbumReleaseEntity}
 * @param <T> {@link AlbumReleaseEntity}
 * @param <ID> id of the entity
 */
public interface AlbumReleaseUpdateOperations<T extends AlbumReleaseEntity, ID> {
    /**
     * Update the duration of the given album by its ID
     * @param id - id of the album to update the duration
     * @param duration - duration of this album
     * @return - updated {@link AlbumRelease}
     */
    @NotNull
    Mono<T> updateAlbumDuration(@NotNull ID id, @Nullable Long duration);

}
