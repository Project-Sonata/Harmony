package com.odeyalo.sonata.harmony.service.event.impl;

import com.odeyalo.sonata.harmony.service.event.AbstractEventPublisher;
import com.odeyalo.sonata.harmony.service.event.EventPublisherDelegate;
import com.odeyalo.sonata.suite.brokers.events.album.BasicAlbumInfoUploadedEvent;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Publish the {@link BasicAlbumInfoUploadedEvent} using {@link EventPublisherDelegate}
 */
@Component
@Log4j2
public class BasicAlbumInfoUploadedEventPublisher extends AbstractEventPublisher<BasicAlbumInfoUploadedEvent> {

    @Autowired
    public BasicAlbumInfoUploadedEventPublisher(EventPublisherDelegate eventPublisherDelegate) {
        super(eventPublisherDelegate);
    }
}
