package com.odeyalo.sonata.harmony.repository.r2dbc.callback.read;

import com.odeyalo.sonata.harmony.entity.TrackEntity;
import com.odeyalo.sonata.harmony.repository.SimplifiedAlbumRepository;
import org.jetbrains.annotations.NotNull;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.r2dbc.mapping.event.AfterConvertCallback;
import org.springframework.data.relational.core.sql.SqlIdentifier;
import org.springframework.stereotype.Component;

@Component
public class TrackAlbumEnhancerAfterConvertCallback implements AfterConvertCallback<TrackEntity> {
    private final SimplifiedAlbumRepository albumRepository;

    @Autowired
    public TrackAlbumEnhancerAfterConvertCallback(@Lazy SimplifiedAlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    @Override
    @NotNull
    public Publisher<TrackEntity> onAfterConvert(@NotNull TrackEntity entity,
                                                 @NotNull SqlIdentifier table) {

        return albumRepository.findById(entity.getAlbumId())
                .doOnNext(entity::setAlbum)
                .thenReturn(entity);
    }
}
