package com.odeyalo.sonata.harmony.repository.r2dbc.callback.read;

import com.odeyalo.sonata.harmony.entity.*;
import com.odeyalo.sonata.harmony.repository.ArtistRepository;
import com.odeyalo.sonata.harmony.repository.r2dbc.R2dbcTrackArtistRepository;
import org.jetbrains.annotations.NotNull;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.r2dbc.mapping.event.AfterConvertCallback;
import org.springframework.data.relational.core.sql.SqlIdentifier;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;


/**
 * AfterConvertCallback used to enhance the TrackEntity with the associated artists.
 */
@Component
public class TrackArtistsEnhancerAfterConvertCallback implements AfterConvertCallback<SimplifiedTrackEntity> {
    private final ArtistRepository artistRepository;
    private final R2dbcTrackArtistRepository r2DbcTrackArtistRepository;

    @Autowired
    public TrackArtistsEnhancerAfterConvertCallback(@Lazy ArtistRepository artistRepository,
                                                    @Lazy R2dbcTrackArtistRepository r2DbcTrackArtistRepository) {
        this.artistRepository = artistRepository;
        this.r2DbcTrackArtistRepository = r2DbcTrackArtistRepository;
    }

    @Override
    @NotNull
    public Publisher<SimplifiedTrackEntity> onAfterConvert(@NotNull SimplifiedTrackEntity entity,
                                                 @NotNull SqlIdentifier table) {

        Mono<List<ArtistEntity>> artistsEntities = r2DbcTrackArtistRepository.findAllByTrackId(entity.getId())
                .map(TrackArtistEntity::getArtistId)
                .collectList()
                .flatMap(artistsId -> artistRepository.findAllById(artistsId).collectList());

        return artistsEntities
                .doOnNext(artists -> entity.setArtists(ArtistContainerEntity.multiple(artists)))
                .thenReturn(entity);
    }
}