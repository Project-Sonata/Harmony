package com.odeyalo.sonata.harmony.service.event.kafka;

import com.odeyalo.sonata.harmony.service.event.EmittingEventSource;
import com.odeyalo.sonata.suite.brokers.events.SonataEvent;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import reactor.core.scheduler.Schedulers;

/**
 * Delegate the messages from the kafka to event source
 */
@Component
public class KafkaMessageDelegator<T extends SonataEvent> {
    private final KafkaConsumer<T> messageProvider;
    private final EmittingEventSource<T> sourceToPush;


    public KafkaMessageDelegator(KafkaConsumer<T> messageProvider,
                                 EmittingEventSource<T> sourceToPush) {
        this.messageProvider = messageProvider;
        this.sourceToPush = sourceToPush;
    }

    @PostConstruct
    void startDelegateMessages() {
        messageProvider.consumeMessages()
                .flatMap(sourceToPush::emitNext)
                .subscribeOn(Schedulers.parallel())
                .subscribe();
    }
}
