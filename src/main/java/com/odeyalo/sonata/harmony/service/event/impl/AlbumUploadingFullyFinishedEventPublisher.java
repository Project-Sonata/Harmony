package com.odeyalo.sonata.harmony.service.event.impl;

import com.odeyalo.sonata.harmony.service.event.AbstractEventPublisher;
import com.odeyalo.sonata.harmony.service.event.EventPublisherDelegate;
import com.odeyalo.sonata.suite.brokers.events.album.AlbumUploadingFullyFinishedEvent;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class AlbumUploadingFullyFinishedEventPublisher extends AbstractEventPublisher<AlbumUploadingFullyFinishedEvent> {

    @Autowired
    public AlbumUploadingFullyFinishedEventPublisher(EventPublisherDelegate eventPublisherDelegate) {
        super(eventPublisherDelegate);
    }
}
