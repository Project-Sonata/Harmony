package com.odeyalo.sonata.harmony.service.event;

import com.odeyalo.sonata.harmony.service.event.kafka.support.KafkaEventTopicNameResolver;
import com.odeyalo.sonata.harmony.support.kafka.ReactiveKafkaProducer;
import com.odeyalo.sonata.suite.brokers.events.SonataEvent;
import com.odeyalo.sonata.suite.brokers.events.album.data.AlbumFullyUploadedInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.function.Supplier;

@Component
public class KafkaEventPublisherDelegate implements EventPublisherDelegate {
    private final KafkaEventTopicNameResolver<SonataEvent> topicNameResolver;
    private final ReactiveKafkaProducer<String, SonataEvent> sender;

    @Autowired
    public KafkaEventPublisherDelegate(KafkaEventTopicNameResolver<SonataEvent> topicNameResolver,
                                       ReactiveKafkaProducer<String, SonataEvent> sender) {
        this.topicNameResolver = topicNameResolver;
        this.sender = sender;
    }

    @Override
    public Mono<Void> publishEvent(SonataEvent event) {
        return topicNameResolver.resolveTopicName(event)
                .map(Supplier::get)
                .flatMap(topic -> sender.send(topic, event))
                .then();
    }
}
