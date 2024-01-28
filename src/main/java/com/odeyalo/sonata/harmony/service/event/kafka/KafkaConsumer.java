package com.odeyalo.sonata.harmony.service.event.kafka;

import com.odeyalo.sonata.suite.brokers.events.SonataEvent;
import reactor.core.publisher.Flux;

/**
 * Consume messages from kafka and return it as infinity hot-publisher Flux
 *
 * @param <T> - type of the event to consume
 */
public interface KafkaConsumer<T extends SonataEvent> {
    /**
     * @return Flux with the messages that was received from kafka
     */
    Flux<T> consumeMessages();

}
