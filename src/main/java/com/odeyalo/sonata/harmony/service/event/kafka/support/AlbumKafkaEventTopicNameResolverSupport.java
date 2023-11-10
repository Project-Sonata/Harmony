package com.odeyalo.sonata.harmony.service.event.kafka.support;

import com.odeyalo.sonata.suite.brokers.events.SonataEvent;
import com.odeyalo.sonata.suite.brokers.events.album.AlbumUploadingFullyFinishedEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class AlbumKafkaEventTopicNameResolverSupport implements KafkaEventTopicNameResolverSupport<SonataEvent> {

    @Override
    @NotNull
    public Mono<KafkaTopicNameSupplier> resolveTopicName(@NotNull SonataEvent event) {
        if ( event instanceof AlbumUploadingFullyFinishedEvent ) {
            return Mono.just(StaticKafkaTopicNameSupplier.create("albums-event-warehouse")); // Should be changed
        }
        return Mono.empty();
    }
}
