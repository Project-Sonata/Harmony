package com.odeyalo.sonata.harmony.service.event;

import com.odeyalo.sonata.suite.brokers.events.SonataEvent;
import org.springframework.core.Ordered;
import reactor.core.publisher.Mono;

/**
 * Listen to {@link SonataEvent} and can handle it
 */
public interface EventListener extends Ordered {
    /**
     * Handle the given event.
     *
     * @param sonataEvent - event to handle or ignore
     * @return - empty mono after execution.
     * Any exception will be ignored and must be handled in listener
     */
    Mono<Void> handleEvent(SonataEvent sonataEvent);

    /**
     * Filter function that uses to filter incoming events
     *
     * @param event - event to filter
     * @return - true if this event listener can handle this event, false otherwise
     */
    boolean supports(SonataEvent event);

    /**
     * @return - default order is middle precendence
     */
    default int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE / 2;
    }
}
