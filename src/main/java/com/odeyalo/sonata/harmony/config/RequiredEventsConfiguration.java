package com.odeyalo.sonata.harmony.config;

import com.odeyalo.sonata.suite.brokers.events.album.AlbumDurationResolvedEvent;
import com.odeyalo.sonata.suite.brokers.events.album.BasicAlbumInfoUploadedEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class RequiredEventsConfiguration {
    /**
     * Required events that should be occurred before sending the {@link com.odeyalo.sonata.suite.brokers.events.album.AlbumUploadingFullyFinishedEvent}
     * @return
     */
    @Bean(name = "albumReleaseUploadingRequiredEventTypes")
    public List<String> albumRequiredEventTypes() {
        return List.of(
                BasicAlbumInfoUploadedEvent.EVENT_TYPE,
                AlbumDurationResolvedEvent.EVENT_TYPE
        );
    }
}
