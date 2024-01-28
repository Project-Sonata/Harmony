package com.odeyalo.sonata.harmony.service.event.kafka;

import com.odeyalo.sonata.suite.brokers.events.SonataEvent;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

/**
 * Consume the SonataEvent type of events from ReactiveKafkaConsumerTemplate
 */
@Component
public class SonataEventKafkaConsumer implements KafkaConsumer<SonataEvent> {
    private final ReactiveKafkaConsumerTemplate<String, SonataEvent> consumer;

    public SonataEventKafkaConsumer(ReactiveKafkaConsumerTemplate<String, SonataEvent> consumer) {
        this.consumer = consumer;
    }

    @Override
    public Flux<SonataEvent> consumeMessages() {
        return consumer.receive().map(ConsumerRecord::value);
    }
}
