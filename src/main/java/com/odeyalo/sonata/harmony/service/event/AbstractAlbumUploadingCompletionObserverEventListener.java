package com.odeyalo.sonata.harmony.service.event;

import com.odeyalo.sonata.harmony.support.utils.CollectionUtils;
import com.odeyalo.sonata.suite.brokers.events.SonataEvent;
import com.odeyalo.sonata.suite.brokers.events.album.AlbumUploadingEvent;
import com.odeyalo.sonata.suite.brokers.events.album.AlbumUploadingFullyFinishedEvent;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Abstract {@link EventListener} that listens to {@link AlbumUploadingEvent} and invokes the {@link #onAllEventsOccurred(AlbumUploadingEvent)} when all events have been processed.
 * invokes {@link #onNotFinishedYet(AlbumUploadingEvent)} otherwise
 */
@Log4j2
public abstract class AbstractAlbumUploadingCompletionObserverEventListener implements EventListener {
    private final List<String> requiredEventsTypes;
    // Maybe rewrite using repository? Keep it as Map for now for simplicity
    private final Map<String, List<String>> occurredEvents = new ConcurrentHashMap<>();

    public AbstractAlbumUploadingCompletionObserverEventListener(List<String> requiredEventsTypes) {
        this.requiredEventsTypes = requiredEventsTypes;
    }

    @Override
    @NotNull
    public Mono<Void> handleEvent(@NotNull SonataEvent sonataEvent) {
        AlbumUploadingEvent albumUploadingEvent = (AlbumUploadingEvent) sonataEvent;

        if ( albumUploadingEvent instanceof AlbumUploadingFullyFinishedEvent ) {
            // we finished executing, just skip it
            log.info("Received AlbumUploadingFullyFinishedEvent, skip it as completed");
            return Mono.empty();
        }

        String albumId = albumUploadingEvent.getAlbumId();

        List<String> alreadyOccurredEventsForAlbum = updateSavedOccurredEvents(albumUploadingEvent, albumId);

        if ( CollectionUtils.containsAll(alreadyOccurredEventsForAlbum, requiredEventsTypes) ) {
            log.info("All required events for album: {} has been occurred", albumId);
            return onAllEventsOccurred(albumUploadingEvent);
        }

        return onNotFinishedYet(albumUploadingEvent);
    }

    @NotNull
    protected abstract Mono<Void> onAllEventsOccurred(AlbumUploadingEvent sonataEvent);

    @NotNull
    protected abstract Mono<Void> onNotFinishedYet(AlbumUploadingEvent sonataEvent);

    @Override
    public boolean supports(@NotNull SonataEvent event) {
        return event instanceof AlbumUploadingEvent;
    }

    @Override
    public int getOrder() {
        return LOWEST_PRECEDENCE;
    }

    @NotNull
    private List<String> updateSavedOccurredEvents(AlbumUploadingEvent albumUploadingEvent, String albumId) {
        List<String> alreadyOccurredEventsForAlbum = occurredEvents.getOrDefault(albumId, Collections.synchronizedList(new ArrayList<>()));

        alreadyOccurredEventsForAlbum.add(albumUploadingEvent.getEventType());

        occurredEvents.put(albumId, alreadyOccurredEventsForAlbum);

        return alreadyOccurredEventsForAlbum;
    }
}
