package com.odeyalo.sonata.harmony.service.album;

import com.odeyalo.sonata.harmony.model.AlbumRelease;
import com.odeyalo.sonata.harmony.repository.AlbumReleaseRepository;
import com.odeyalo.sonata.harmony.support.converter.AlbumReleaseConverter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * Default implementation that uses {@link AlbumReleaseRepository} and {@link AlbumReleaseConverter}
 */
@Component
@RequiredArgsConstructor
public final class DefaultAlbumReleaseService implements AlbumReleaseService {
    private final AlbumReleaseRepository albumReleaseRepository;
    private final AlbumReleaseConverter albumReleaseConverter;

    @Override
    @NotNull
    public Mono<AlbumRelease> findById(@NotNull String id) {
        return albumReleaseRepository.findById(Long.valueOf(id))
                .map(albumReleaseConverter::toAlbumRelease);
    }
}