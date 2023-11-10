package com.odeyalo.sonata.harmony.service.event.kafka.support;

import com.odeyalo.sonata.suite.brokers.events.SonataEvent;
import org.jetbrains.annotations.NotNull;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * Uses chain of KafkaEventTopicNameResolverSupport<SonataEvent> and return first non-empty result
 *
 * Uses SonataEvent as object to resolve from
 */
public class ChainedSonataEventTopicNameResolver implements KafkaEventTopicNameResolver<SonataEvent> {
    private final List<KafkaEventTopicNameResolverSupport<SonataEvent>> resolvers;

    public ChainedSonataEventTopicNameResolver(List<KafkaEventTopicNameResolverSupport<SonataEvent>> resolvers) {
        this.resolvers = resolvers;
    }

    @Override
    @NotNull
    public Mono<KafkaTopicNameSupplier> resolveTopicName(@NotNull SonataEvent from) {
        return Flux.fromIterable(resolvers)
                .flatMap(resolver -> resolver.resolveTopicName(from))
                .next();
    }
}
