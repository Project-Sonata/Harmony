package com.odeyalo.sonata.harmony.service.event.kafka.support;

import java.util.function.Supplier;

/**
 * Supply the topic name.  Implementations can provide static names or update them everytime.
 *
 * @see StaticKafkaTopicNameSupplier
 */
public interface KafkaTopicNameSupplier extends Supplier<String> {

}
