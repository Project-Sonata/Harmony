package com.odeyalo.sonata.harmony.service.event;

import com.odeyalo.sonata.suite.brokers.events.SonataEvent;
import reactor.core.publisher.Mono;

/**
 * Extension for {@link EventSource} that add ability to push events to EventSource.
 * Supports only the same event types.
 *
 * Implements AutoCloseable for try-with-resources use cases
 * @param <T> - event to push
 */
public interface EmittingEventSource<T extends SonataEvent> extends EventSource<T>, AutoCloseable {

    Mono<Void> emitNext(T event);

    @Override
    void close();
}
