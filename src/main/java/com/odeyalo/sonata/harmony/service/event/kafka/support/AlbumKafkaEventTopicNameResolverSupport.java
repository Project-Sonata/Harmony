package com.odeyalo.sonata.harmony.service.event.kafka.support;

import com.odeyalo.sonata.suite.brokers.events.SonataEvent;
import com.odeyalo.sonata.suite.brokers.events.album.AlbumUploadingFullyFinishedEvent;
import com.odeyalo.sonata.suite.brokers.events.album.BasicAlbumInfoUploadedEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class AlbumKafkaEventTopicNameResolverSupport implements KafkaEventTopicNameResolverSupport<SonataEvent> {

    @Override
    @NotNull
    public Mono<KafkaTopicNameSupplier> resolveTopicName(@NotNull SonataEvent event) {
        // bad practice, will update it when suite-commons 0.0.9 will be released
        if ( event instanceof AlbumUploadingFullyFinishedEvent || event instanceof BasicAlbumInfoUploadedEvent ) {
            return Mono.just(StaticKafkaTopicNameSupplier.create("albums-event-warehouse")); // Should be changed
        }
        return Mono.empty();
    }
}
