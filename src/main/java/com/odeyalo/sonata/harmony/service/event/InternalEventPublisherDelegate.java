package com.odeyalo.sonata.harmony.service.event;

import com.odeyalo.sonata.suite.brokers.events.SonataEvent;
import org.springframework.util.Assert;
import reactor.core.publisher.Mono;

/**
 * EventPublisherDelegate implementation that just pushes the events to {@link EmittingEventSource}.
 */
public class InternalEventPublisherDelegate implements EventPublisherDelegate {
    private final EmittingEventSource<SonataEvent> emitter;

    public InternalEventPublisherDelegate(EmittingEventSource<SonataEvent> emitter) {
        this.emitter = emitter;
    }

    @Override
    public Mono<Void> publishEvent(SonataEvent event) {
        Assert.notNull(event, "Event cannot be null!");
        return emitter.emitNext(event);
    }
}
