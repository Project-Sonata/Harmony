package com.odeyalo.sonata.harmony.service.event.impl;

import com.odeyalo.sonata.harmony.service.event.EventPublisher;
import com.odeyalo.sonata.harmony.service.event.EventPublisherDelegate;
import com.odeyalo.sonata.suite.brokers.events.album.AlbumUploadingFullyFinishedEvent;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Log4j2
public class AlbumUploadingFullyFinishedEventPublisher implements EventPublisher<AlbumUploadingFullyFinishedEvent> {
    private final EventPublisherDelegate eventPublisherDelegate;

    public AlbumUploadingFullyFinishedEventPublisher(EventPublisherDelegate eventPublisherDelegate) {
        this.eventPublisherDelegate = eventPublisherDelegate;
        log.info("Using the following delegate to publish the events: {}", eventPublisherDelegate);
    }

    @Override
    public Mono<Void> publishEvent(AlbumUploadingFullyFinishedEvent event) {
        return eventPublisherDelegate.publishEvent(event);
    }
}
