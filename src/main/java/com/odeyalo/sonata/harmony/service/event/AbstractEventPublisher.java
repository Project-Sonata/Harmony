package com.odeyalo.sonata.harmony.service.event;

import com.odeyalo.sonata.suite.brokers.events.SonataEvent;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;

/**
 * Abstract implementation of {@link EventPublisher} that publish events using {@link EventPublisherDelegate}
 * @param <T> - type of the event to publish
 */
@Log4j2
public class AbstractEventPublisher<T extends SonataEvent> implements EventPublisher<T> {
    private final EventPublisherDelegate eventPublisherDelegate;

    public AbstractEventPublisher(EventPublisherDelegate eventPublisherDelegate) {
        this.eventPublisherDelegate = eventPublisherDelegate;
        log.info("Using the following delegate to publish the events: {}", eventPublisherDelegate);
    }

    @Override
    public Mono<Void> publishEvent(T event) {
        return eventPublisherDelegate.publishEvent(event);
    }
}
