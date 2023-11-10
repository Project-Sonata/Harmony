package com.odeyalo.sonata.harmony.support.kafka;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.reactivestreams.Publisher;
import org.springframework.messaging.Message;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.SenderRecord;
import reactor.kafka.sender.SenderResult;

/**
 * Central interface to send the messages using Kafka in reactive way.
 *
 * @param <K> - key
 * @param <V> - value
 */
public interface ReactiveKafkaProducer<K, V> {

    <T> Flux<SenderResult<T>> sendTransactionally(Publisher<? extends SenderRecord<K, V, T>> records);

    <T> Mono<SenderResult<T>> sendTransactionally(SenderRecord<K, V, T> record);

    Mono<SenderResult<Void>> send(String topic, V value);

    Mono<SenderResult<Void>> send(String topic, K key, V value);

    Mono<SenderResult<Void>> send(String topic, int partition, K key, V value);

    Mono<SenderResult<Void>> send(String topic, int partition, long timestamp, K key, V value);

    Mono<SenderResult<Void>> send(String topic, Message<?> message);

    Mono<SenderResult<Void>> send(ProducerRecord<K, V> record);

    <T> Mono<SenderResult<T>> send(SenderRecord<K, V, T> record);

    <T> Flux<SenderResult<T>> send(Publisher<? extends SenderRecord<K, V, T>> records);
}
