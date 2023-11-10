package com.odeyalo.sonata.harmony.support.kafka;

import lombok.experimental.Delegate;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Component;

@Component
public class TemplateDelegateReactiveKafkaProducer<K, V> implements ReactiveKafkaProducer<K, V> {
    @Delegate
    private final ReactiveKafkaProducerTemplate<K, V> delegate;

    public TemplateDelegateReactiveKafkaProducer(ReactiveKafkaProducerTemplate<K, V> delegate) {
        this.delegate = delegate;
    }
}
