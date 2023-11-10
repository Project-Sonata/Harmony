package com.odeyalo.sonata.harmony.service.event.kafka.support;

import com.odeyalo.sonata.suite.brokers.events.SonataEvent;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import testing.events.MockSonataEvent;

import java.util.List;

class ChainedSonataEventTopicNameResolverTest {

    @Test
    void shouldReturnTopicNameSupplierForMatchingEvent() {
        var expectedSupplier = StaticKafkaTopicNameSupplier.create("Water");
        KafkaEventTopicNameResolverSupport<SonataEvent> resolverSupport =
                (sonataEvent) -> Mono.just(expectedSupplier);

        var testable = new ChainedSonataEventTopicNameResolver(
                List.of(resolverSupport)
        );

        var event = new MockSonataEvent("hello");

        testable.resolveTopicName(event)
                .as(StepVerifier::create)
                .expectNext(expectedSupplier)
                .verifyComplete();
    }

    @Test
    void shouldReturnTopicNameSupplierForMatchingEventIfMultipleResolversIsUsing() {
        var expectedSupplier = StaticKafkaTopicNameSupplier.create("Water");

        KafkaEventTopicNameResolverSupport<SonataEvent> emptyResolver =
                (sonataEvent) -> Mono.empty();
        KafkaEventTopicNameResolverSupport<SonataEvent> resolver =
                (sonataEvent) -> Mono.just(expectedSupplier);


        var testable = new ChainedSonataEventTopicNameResolver(
                List.of(emptyResolver, resolver)
        );

        var event = new MockSonataEvent("hello");

        testable.resolveTopicName(event)
                .as(StepVerifier::create)
                .expectNext(expectedSupplier)
                .verifyComplete();
    }
}