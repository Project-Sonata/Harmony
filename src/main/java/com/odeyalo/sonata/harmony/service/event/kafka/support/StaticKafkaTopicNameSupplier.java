package com.odeyalo.sonata.harmony.service.event.kafka.support;

import org.springframework.util.Assert;

/**
 * Static implementation of KafkaTopicNameSupplier. Always returns the same value
 */
public class StaticKafkaTopicNameSupplier implements KafkaTopicNameSupplier {
    private final String topicName;

    public StaticKafkaTopicNameSupplier(String topicName) {
        Assert.notNull(topicName, "Topic name cannot be null!");
        this.topicName = topicName;
    }

    public static StaticKafkaTopicNameSupplier create(String topicName) {
        return new StaticKafkaTopicNameSupplier(topicName);
    }

    @Override
    public String get() {
        return topicName;
    }
}
