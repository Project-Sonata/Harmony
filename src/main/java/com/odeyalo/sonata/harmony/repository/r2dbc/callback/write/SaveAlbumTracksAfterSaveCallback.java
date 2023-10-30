package com.odeyalo.sonata.harmony.repository.r2dbc.callback.write;

import com.odeyalo.sonata.harmony.entity.AlbumReleaseEntity;
import com.odeyalo.sonata.harmony.repository.SimplifiedTrackRepository;
import org.jetbrains.annotations.NotNull;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.r2dbc.mapping.OutboundRow;
import org.springframework.data.r2dbc.mapping.event.AfterSaveCallback;
import org.springframework.data.relational.core.sql.SqlIdentifier;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

/**
 * Save the tracks from album entity after saving the AlbumReleaseEntity
 */
@Component
public class SaveAlbumTracksAfterSaveCallback implements AfterSaveCallback<AlbumReleaseEntity> {
    private final SimplifiedTrackRepository trackRepository;

    @Autowired
    public SaveAlbumTracksAfterSaveCallback(@Lazy SimplifiedTrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }

    @Override
    @NotNull
    public Publisher<AlbumReleaseEntity> onAfterSave(@NotNull AlbumReleaseEntity album,
                                                     @NotNull OutboundRow outboundRow,
                                                     @NotNull SqlIdentifier table) {

        return Flux.fromIterable(album.getTracks())
                .doOnNext(track -> track.setAlbumId(album.getId()))
                .collectList()
                .flatMap(tracks -> trackRepository.saveAll(tracks).then())
                .thenReturn(album);
    }
}
