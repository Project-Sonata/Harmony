package com.odeyalo.sonata.harmony.repository.r2dbc.callback.write;

import com.odeyalo.sonata.harmony.entity.AlbumReleaseEntity;
import com.odeyalo.sonata.harmony.repository.AlbumCoverImageRepository;
import org.jetbrains.annotations.NotNull;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.r2dbc.mapping.OutboundRow;
import org.springframework.data.r2dbc.mapping.event.AfterSaveCallback;
import org.springframework.data.relational.core.sql.SqlIdentifier;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Save album cover images after AlbumReleaseEntity has been saved
 */
@Component
public class SaveAlbumCoverImagesAfterSaveCallback implements AfterSaveCallback<AlbumReleaseEntity> {
    private final AlbumCoverImageRepository albumCoverImageRepository;

    @Autowired
    public SaveAlbumCoverImagesAfterSaveCallback(@Lazy AlbumCoverImageRepository albumCoverImageRepository) {
        this.albumCoverImageRepository = albumCoverImageRepository;
    }

    @Override
    @NotNull
    public Publisher<AlbumReleaseEntity> onAfterSave(@NotNull AlbumReleaseEntity entity,
                                                     @NotNull OutboundRow outboundRow,
                                                     @NotNull SqlIdentifier table) {
        Long albumId = entity.getId();

        return Flux.fromIterable(entity.getImages())
                .doOnNext(imageEntity -> imageEntity.setAlbumId(albumId))
                .flatMap(albumCoverImageRepository::save)
                .then(Mono.just(entity));
    }
}
