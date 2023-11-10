package com.odeyalo.sonata.harmony.service.event.kafka.support;

import org.jetbrains.annotations.NotNull;
import reactor.core.publisher.Mono;

/**
 * Resolve the topic name for the object T. Designed to be extendable and easy-to-use
 *
 * @param <T> - any Object to resolve topic from
 */
public interface KafkaEventTopicNameResolver<T> {
    /**
     * Resolve topic name from the T
     *
     * @param from - generic type to resolve from
     * @return - return Mono with KafkaTopicNameSupplier.
     * It can return empty mono and return default topic name.
     * Never throws exceptions.
     */
    @NotNull
    Mono<KafkaTopicNameSupplier> resolveTopicName(@NotNull T from);

}
