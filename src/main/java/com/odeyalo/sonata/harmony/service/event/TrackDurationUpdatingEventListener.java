package com.odeyalo.sonata.harmony.service.event;

import com.odeyalo.sonata.harmony.entity.SimplifiedTrackEntity;
import com.odeyalo.sonata.harmony.repository.SimplifiedTrackRepository;
import com.odeyalo.sonata.suite.brokers.events.SonataEvent;
import com.odeyalo.sonata.suite.brokers.events.album.AlbumDurationResolvedEvent;
import com.odeyalo.sonata.suite.brokers.events.album.data.AlbumDurationResolvedPayload;
import com.odeyalo.sonata.suite.brokers.events.album.data.TrackDuration;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.Optional;

/**
 * Update duration of the track
 */
@Component
@Log4j2
public class TrackDurationUpdatingEventListener implements EventListener {
    private final SimplifiedTrackRepository trackRepository;

    public TrackDurationUpdatingEventListener(SimplifiedTrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }

    @Override
    public Mono<Void> handleEvent(SonataEvent sonataEvent) {
        AlbumDurationResolvedEvent albumDurationResolvedEvent = (AlbumDurationResolvedEvent) sonataEvent;
        AlbumDurationResolvedPayload payload = albumDurationResolvedEvent.getBody();

        return trackRepository.findAllByAlbumId(Long.valueOf(payload.getAlbumId()))
                .doOnNext(simplifiedTrackEntity -> {
                    Optional<TrackDuration> trackDurationOptional = findByTrackId(payload, simplifiedTrackEntity);

                    if ( trackDurationOptional.isEmpty() ) {
                        log.warn("There is no existing track id. payload: {}, current track entity: {}", payload, simplifiedTrackEntity);
                        return;
                    }

                    TrackDuration trackDuration = trackDurationOptional.get();

                    simplifiedTrackEntity.setDurationMs(trackDuration.getDurationMs());
                })
                .flatMap(trackRepository::save)
                .then();

    }

    @NotNull
    private static Optional<TrackDuration> findByTrackId(AlbumDurationResolvedPayload payload, SimplifiedTrackEntity simplifiedTrackEntity) {
        return payload.getTrackDurationContainer().stream()
                .filter(x -> Objects.equals(Long.valueOf(x.getTrackId()), simplifiedTrackEntity.getId()))
                .findFirst();
    }

    @Override
    public boolean supports(SonataEvent event) {
        return event instanceof AlbumDurationResolvedEvent;
    }
}
