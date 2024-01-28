package com.odeyalo.sonata.harmony.config.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.odeyalo.sonata.harmony.config.event.EventTypeProviderContainer;
import com.odeyalo.sonata.harmony.support.kafka.HeadersPopulatorJsonTypeResolver;
import com.odeyalo.sonata.suite.brokers.events.SonataEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.kafka.support.mapping.DefaultJackson2JavaTypeMapper;
import org.springframework.kafka.support.mapping.Jackson2JavaTypeMapper;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import reactor.kafka.receiver.ReceiverOptions;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfiguration {

    @Bean
    public ReceiverOptions<String, SonataEvent> albumEventWarehouseKafkaReceiver(KafkaProperties props,
                                                                                 JsonDeserializer<SonataEvent> jsonDeserializer) {
        Map<String, Object> consumerProps = new HashMap<>();
        consumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, props.getBootstrapServers());
        consumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, props.getConsumer().getGroupId());
        consumerProps.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);

        return ReceiverOptions.<String, SonataEvent>create(consumerProps)
                .withValueDeserializer(jsonDeserializer)
                .subscription(Collections.singleton("albums-event-warehouse"));
    }


    @Bean
    public JsonDeserializer<SonataEvent> sonataEventJsonDeserializer(ObjectMapper objectMapper,
                                                                     Jackson2JavaTypeMapper mapper,
                                                                     EventTypeProviderContainer container) {

        JsonDeserializer<SonataEvent> deserializer = new JsonDeserializer<>(objectMapper);
        deserializer.trustedPackages("*");
        deserializer.setTypeResolver(new HeadersPopulatorJsonTypeResolver(mapper, container));
        return deserializer;
    }

    @Bean
    public Jackson2JavaTypeMapper jackson2JavaTypeMapper() {
        DefaultJackson2JavaTypeMapper mapper = new DefaultJackson2JavaTypeMapper();
        mapper.addTrustedPackages("*");
        return mapper;
    }

    @Bean
    public ReactiveKafkaConsumerTemplate<String, SonataEvent> reactiveKafkaConsumerTemplate(ReceiverOptions<String, SonataEvent> kafkaReceiverOptions) {
        return new ReactiveKafkaConsumerTemplate<>(kafkaReceiverOptions);
    }
}
