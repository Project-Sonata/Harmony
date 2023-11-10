package com.odeyalo.sonata.harmony.service.event.kafka.support;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StaticKafkaTopicNameSupplierTest {

    @Test
    void shouldReturnSameValues() {
        var supplier = StaticKafkaTopicNameSupplier.create("miku-event-store");

        assertThat(supplier.get()).isEqualTo("miku-event-store");
        // Checking twice to ensure that values has not been erased
        assertThat(supplier.get()).isEqualTo("miku-event-store");
    }
}