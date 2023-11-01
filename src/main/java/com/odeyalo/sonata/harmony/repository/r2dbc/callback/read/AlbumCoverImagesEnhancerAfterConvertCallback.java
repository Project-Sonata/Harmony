package com.odeyalo.sonata.harmony.repository.r2dbc.callback.read;

import com.odeyalo.sonata.harmony.entity.AlbumCoverImageContainerEntity;
import com.odeyalo.sonata.harmony.entity.AlbumReleaseEntity;
import com.odeyalo.sonata.harmony.repository.AlbumCoverImageRepository;
import org.jetbrains.annotations.NotNull;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.r2dbc.mapping.event.AfterConvertCallback;
import org.springframework.data.relational.core.sql.SqlIdentifier;
import org.springframework.stereotype.Component;

/**
 * Add the album cover images to album after it was converted
 */
@Component
public class AlbumCoverImagesEnhancerAfterConvertCallback implements AfterConvertCallback<AlbumReleaseEntity> {
    private final AlbumCoverImageRepository albumCoverImageRepository;

    @Autowired
    public AlbumCoverImagesEnhancerAfterConvertCallback(@Lazy AlbumCoverImageRepository albumCoverImageRepository) {
        this.albumCoverImageRepository = albumCoverImageRepository;
    }

    @Override
    @NotNull
    public Publisher<AlbumReleaseEntity> onAfterConvert(@NotNull AlbumReleaseEntity entity,
                                                        @NotNull SqlIdentifier table) {

        return albumCoverImageRepository.findAllByAlbumId(entity.getId())
                .collectList()
                .doOnNext(images -> entity.setImages(AlbumCoverImageContainerEntity.of(images)))
                .thenReturn(entity);
    }
}
