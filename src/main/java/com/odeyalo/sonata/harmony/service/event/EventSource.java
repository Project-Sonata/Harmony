package com.odeyalo.sonata.harmony.service.event;

import com.odeyalo.sonata.suite.brokers.events.SonataEvent;
import reactor.core.publisher.Flux;

/**
 * Consume the messages from some kind of store and return it in {@link #getEvents()}.
 * @param <T> - type of the event to consume
 */
public interface EventSource<T extends SonataEvent> {
    /**
     * Returns hot-stream Flux with new messages only
     * @return - infinity flux with events.
     */
    Flux<T> getEvents();

}