package com.odeyalo.sonata.harmony.service.event;

import com.odeyalo.sonata.harmony.service.album.AlbumReleaseService;
import com.odeyalo.sonata.harmony.service.event.impl.AlbumUploadingFullyFinishedEventPublisher;
import com.odeyalo.sonata.harmony.support.converter.external.UploadedAlbumInfoDtoConverter;
import com.odeyalo.sonata.suite.brokers.events.album.AlbumUploadingEvent;
import com.odeyalo.sonata.suite.brokers.events.album.AlbumUploadingFullyFinishedEvent;
import com.odeyalo.sonata.suite.brokers.events.album.data.AlbumFullyUploadedInfo;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * {@link EventListener} that just observes the {@link AlbumUploadingEvent}s and when all required events have been occurred - sends a new {@link AlbumUploadingFullyFinishedEvent}
 */
@Component
public final class EventPublisherAlbumUploadingCompletionObserverEventListener extends AbstractAlbumUploadingCompletionObserverEventListener {
    private final AlbumUploadingFullyFinishedEventPublisher eventPublisher;
    private final AlbumReleaseService albumReleaseService;
    private final UploadedAlbumInfoDtoConverter uploadedAlbumInfoDtoConverter;

    @Autowired
    public EventPublisherAlbumUploadingCompletionObserverEventListener(@Qualifier("albumReleaseUploadingRequiredEventTypes") List<String> requiredEventsTypes,
                                                                       AlbumUploadingFullyFinishedEventPublisher eventPublisher,
                                                                       AlbumReleaseService albumReleaseService,
                                                                       UploadedAlbumInfoDtoConverter uploadedAlbumInfoDtoConverter) {
        super(requiredEventsTypes);
        this.eventPublisher = eventPublisher;
        this.albumReleaseService = albumReleaseService;
        this.uploadedAlbumInfoDtoConverter = uploadedAlbumInfoDtoConverter;
    }

    @Override
    @NotNull
    protected Mono<Void> onAllEventsOccurred(AlbumUploadingEvent sonataEvent) {
        String albumId = sonataEvent.getAlbumId();

        return albumReleaseService.findById(albumId)
                .map(uploadedAlbumInfoDtoConverter::toUploadedAlbumInfoDto)
                .map(infoDto -> new AlbumUploadingFullyFinishedEvent(AlbumFullyUploadedInfo.of(infoDto)))
                .flatMap(eventPublisher::publishEvent);
    }

    @Override
    @NotNull
    protected Mono<Void> onNotFinishedYet(AlbumUploadingEvent sonataEvent) {
        return Mono.empty();
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
