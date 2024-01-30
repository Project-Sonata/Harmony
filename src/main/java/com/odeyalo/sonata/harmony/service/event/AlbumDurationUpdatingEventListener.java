package com.odeyalo.sonata.harmony.service.event;

import com.odeyalo.sonata.harmony.repository.AlbumReleaseRepository;
import com.odeyalo.sonata.suite.brokers.events.SonataEvent;
import com.odeyalo.sonata.suite.brokers.events.album.AlbumDurationResolvedEvent;
import com.odeyalo.sonata.suite.brokers.events.album.data.AlbumDurationResolvedPayload;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * Update total time of the album
 */
@Component
public class AlbumDurationUpdatingEventListener implements EventListener {
    private final AlbumReleaseRepository albumReleaseRepository;

    public AlbumDurationUpdatingEventListener(AlbumReleaseRepository albumReleaseRepository) {
        this.albumReleaseRepository = albumReleaseRepository;
    }

    @Override
    public Mono<Void> handleEvent(SonataEvent sonataEvent) {
        AlbumDurationResolvedPayload payload = getAlbumDurationResolvedPayload(sonataEvent);

        Long albumId = Long.parseLong(payload.getAlbumId());

        return albumReleaseRepository.updateAlbumDuration(albumId, payload.getTotalDurationMs())
                .then();
    }

    @Override
    public boolean supports(SonataEvent event) {
        return event instanceof AlbumDurationResolvedEvent;
    }

    private static AlbumDurationResolvedPayload getAlbumDurationResolvedPayload(SonataEvent sonataEvent) {
        AlbumDurationResolvedEvent albumDurationResolvedEvent = (AlbumDurationResolvedEvent) sonataEvent;

        return albumDurationResolvedEvent.getBody();
    }
}
