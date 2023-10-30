package com.odeyalo.sonata.harmony.repository.r2dbc.callback.write;

import com.odeyalo.sonata.harmony.entity.SimplifiedTrackEntity;
import com.odeyalo.sonata.harmony.entity.TrackArtistEntity;
import com.odeyalo.sonata.harmony.repository.r2dbc.R2dbcTrackArtistRepository;
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
 * Associate the TrackEntity with the Artists using the conjunction table TrackArtistEntity
 */
@Component
public class TrackArtistAssociationAfterSaveCallback implements AfterSaveCallback<SimplifiedTrackEntity> {
    private final R2dbcTrackArtistRepository trackArtistRepository;

    @Autowired
    public TrackArtistAssociationAfterSaveCallback(@Lazy R2dbcTrackArtistRepository trackArtistRepository) {
        this.trackArtistRepository = trackArtistRepository;
    }

    @Override
    @NotNull
    public Publisher<SimplifiedTrackEntity> onAfterSave(@NotNull SimplifiedTrackEntity track,
                                              @NotNull OutboundRow outboundRow,
                                              @NotNull SqlIdentifier table) {

        Flux<TrackArtistEntity> trackArtists = Flux.fromIterable(track.getArtists())
                .map(artist -> TrackArtistEntity.pairOf(track.getId(), artist.getId()));

        return trackArtistRepository.saveAll(trackArtists)
                .then(Mono.just(track));
    }
}
