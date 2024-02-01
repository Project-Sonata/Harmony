package com.odeyalo.sonata.harmony.service.album;

import com.odeyalo.sonata.harmony.model.AlbumRelease;
import org.jetbrains.annotations.NotNull;
import reactor.core.publisher.Mono;

/**
 * Service class to get the {@link AlbumRelease} by its id
 */
public interface AlbumReleaseService {

    @NotNull
    Mono<AlbumRelease> findById(@NotNull String id);

}
