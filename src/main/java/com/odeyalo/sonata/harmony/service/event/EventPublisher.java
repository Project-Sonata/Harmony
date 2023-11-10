package com.odeyalo.sonata.harmony.service.event;

import com.odeyalo.sonata.suite.brokers.events.SonataEvent;
import reactor.core.publisher.Mono;

/**
 * The EventPublisher interface is responsible for publishing a specific event type.
 * Implementations of this interface are designed to publish a particular type of event,
 * providing a means to propagate the event to relevant components or systems within the application.
 *
 * @param <T> event type that this event publisher publish
 */
public interface EventPublisher<T extends SonataEvent> {

    Mono<Void> publishEvent(T event);

}