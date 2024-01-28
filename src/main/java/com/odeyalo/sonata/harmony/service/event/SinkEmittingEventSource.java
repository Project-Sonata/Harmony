package com.odeyalo.sonata.harmony.service.event;

import com.odeyalo.sonata.suite.brokers.events.SonataEvent;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

/**
 * EmittingEventSource impl that uses Sink to push the events to EventSource
 * @param <T> event to emit and receive
 */
@Component
public class SinkEmittingEventSource<T extends SonataEvent> implements EmittingEventSource<T> {
    private final Sinks.Many<T> sink = Sinks.many().multicast().onBackpressureBuffer();

    @Override
    public Mono<Void> emitNext(T event) {
        sink.tryEmitNext(event);
        return Mono.empty();
    }

    @Override
    public Flux<T> getEvents() {
        return sink.asFlux();
    }

    @Override
    public void close() {
        sink.tryEmitComplete();
    }
}
