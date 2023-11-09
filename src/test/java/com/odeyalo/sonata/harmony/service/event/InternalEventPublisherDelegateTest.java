package com.odeyalo.sonata.harmony.service.event;

import com.odeyalo.sonata.suite.brokers.events.AbstractEvent;
import com.odeyalo.sonata.suite.brokers.events.SonataEvent;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class InternalEventPublisherDelegateTest {

    @Test
    void shouldNotifyEventSourceOnNewEvent() {
        var eventSource = new SinkEmittingEventSource<>();
        var testable = new InternalEventPublisherDelegate(eventSource);

        Flux<SonataEvent> receivedEvents = eventSource.getEvents();

        var event = new MockSonataEvent("I love Miku!");

        testable.publishEvent(event)
                .as(StepVerifier::create)
                .verifyComplete();

        eventSource.close();

        StepVerifier.create(receivedEvents)
                .expectNext(event)
                .verifyComplete();
    }

    @Test
    void shouldThrowExceptionOnNull() {
        var eventSource = new SinkEmittingEventSource<>();
        var testable = new InternalEventPublisherDelegate(eventSource);

        Flux<SonataEvent> receivedEvents = eventSource.getEvents();

        assertThatThrownBy(() -> testable.publishEvent(null).block())
                .isInstanceOf(IllegalArgumentException.class);

        eventSource.close();
        // Don't expect any events in Flux
        StepVerifier.create(receivedEvents)
                .verifyComplete();
    }

    private static class MockSonataEvent extends AbstractEvent<String> {
        public MockSonataEvent(String body) {
            super(body);
        }

        public MockSonataEvent(String id, long creationTime, String body) {
            super(id, creationTime, body);
        }
    }
}