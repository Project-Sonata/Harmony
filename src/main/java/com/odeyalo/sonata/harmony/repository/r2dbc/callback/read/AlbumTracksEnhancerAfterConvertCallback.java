package com.odeyalo.sonata.harmony.repository.r2dbc.callback.read;

import com.odeyalo.sonata.harmony.entity.AlbumReleaseEntity;
import com.odeyalo.sonata.harmony.entity.TrackContainerEntity;
import com.odeyalo.sonata.harmony.repository.SimplifiedTrackRepository;
import org.jetbrains.annotations.NotNull;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.r2dbc.mapping.event.AfterConvertCallback;
import org.springframework.data.relational.core.sql.SqlIdentifier;
import org.springframework.stereotype.Component;

/**
 * Enhance album with saved tracks from database
 */
@Component
public class AlbumTracksEnhancerAfterConvertCallback implements AfterConvertCallback<AlbumReleaseEntity> {
    private final SimplifiedTrackRepository trackRepository;

    @Autowired
    public AlbumTracksEnhancerAfterConvertCallback(@Lazy SimplifiedTrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }

    @Override
    @NotNull
    public Publisher<AlbumReleaseEntity> onAfterConvert(@NotNull AlbumReleaseEntity entity,
                                                        @NotNull SqlIdentifier table) {

        return trackRepository.findAllByAlbumId(entity.getId())
                .collectList()
                .doOnNext(tracks -> entity.setTracks(TrackContainerEntity.wrap(tracks)))
                .thenReturn(entity);
    }
}
