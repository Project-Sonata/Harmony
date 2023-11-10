package com.odeyalo.sonata.harmony.service.event.kafka.support;

import reactor.core.publisher.Mono;

/**
 * Supporter for kafka event topic resolving for specific type T
 * @param <T> - type T to resolve topic from
 *
 * @see ChainedSonataEventTopicNameResolver
 */
public interface KafkaEventTopicNameResolverSupport<T> {

    Mono<KafkaTopicNameSupplier> resolveTopicName(T from);

}
