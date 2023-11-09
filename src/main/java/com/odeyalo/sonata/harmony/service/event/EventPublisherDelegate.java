package com.odeyalo.sonata.harmony.service.event;

import com.odeyalo.sonata.suite.brokers.events.SonataEvent;
import reactor.core.publisher.Mono;

/**
 * Used as middleware between {@link EventPublisher} and actual event destination store.
 *
 * @see InternalEventPublisherDelegate
 */
public interface EventPublisherDelegate {

    Mono<Void> publishEvent(SonataEvent event);

}
