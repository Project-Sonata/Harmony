package com.odeyalo.sonata.harmony.config.kafka;

import com.odeyalo.sonata.suite.brokers.events.SonataEvent;
import com.odeyalo.sonata.suite.brokers.events.album.data.AlbumFullyUploadedInfo;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.kafka.support.serializer.JsonSerializer;
import reactor.kafka.sender.SenderOptions;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfiguration {

    @Bean
    public ReactiveKafkaProducerTemplate<String, SonataEvent> kafkaSender() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        SenderOptions<String, SonataEvent> senderOptions =
                SenderOptions.<String, SonataEvent>create(props)
                        .maxInFlight(1024);

        return new ReactiveKafkaProducerTemplate<>(senderOptions);
    }
}
