package com.odeyalo.sonata.harmony.repository.r2dbc.callback.read;

import com.odeyalo.sonata.harmony.entity.*;
import com.odeyalo.sonata.harmony.repository.ArtistRepository;
import com.odeyalo.sonata.harmony.repository.r2dbc.R2dbcAlbumArtistsRepository;
import org.jetbrains.annotations.NotNull;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.r2dbc.mapping.event.AfterConvertCallback;
import org.springframework.data.relational.core.sql.SqlIdentifier;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class AlbumArtistsEnhancerAfterConvertCallback implements AfterConvertCallback<SimplifiedAlbumEntity> {
    private final R2dbcAlbumArtistsRepository albumArtistsRepository;
    private final ArtistRepository artistRepository;

    @Autowired
    public AlbumArtistsEnhancerAfterConvertCallback(@Lazy R2dbcAlbumArtistsRepository albumArtistsRepository,
                                                    @Lazy ArtistRepository artistRepository) {
        this.albumArtistsRepository = albumArtistsRepository;
        this.artistRepository = artistRepository;
    }

    @Override
    @NotNull
    public Publisher<SimplifiedAlbumEntity> onAfterConvert(@NotNull SimplifiedAlbumEntity entity,
                                                        @NotNull SqlIdentifier table) {

        Flux<AlbumArtistEntity> albumArtists = albumArtistsRepository.findAllByAlbumId(entity.getId());

        Mono<List<ArtistEntity>> artistsMono = albumArtists.map(AlbumArtistEntity::getArtistId).collectList()
                .flatMapMany(artistRepository::findAllById).collectList();

        return artistsMono
                .map(ArtistContainerEntity::multiple)
                .doOnNext(entity::setArtists)
                .thenReturn(entity);
    }
}
