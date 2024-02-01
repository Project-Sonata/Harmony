package com.odeyalo.sonata.harmony.service.event;

import com.odeyalo.sonata.suite.brokers.events.SonataEvent;
import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.Collection;
import java.util.List;

/**
 * Capable to auto-invoke registered listeners.
 */
@Component
@Log4j2
public class ListenerInvocationCapableEventManager implements EventListenerRegistry {
    private final List<EventListener> listeners;
    private final EventSource<SonataEvent> eventSource;

    public ListenerInvocationCapableEventManager(List<EventListener> listeners, EventSource<SonataEvent> eventSource) {
        this.listeners = listeners;
        this.eventSource = eventSource;
    }

    @PostConstruct
    public void startEventListening() {

        eventSource.getEvents()
                .flatMap(event -> Flux.fromIterable(listeners)
                        .doOnNext(listener -> log.debug("Handling the {} with {}", event, listener))
                        .filter(listener -> listener.supports(event))
                        .flatMap(listener -> listener.handleEvent(event)))
                .subscribeOn(Schedulers.parallel())
                .subscribe();
    }

    @Override
    public void addListener(@NotNull EventListener eventListener) {
        listeners.add(eventListener);
    }

    @Override
    public void deleteListener(@NotNull EventListener eventListener) {
        listeners.remove(eventListener);
    }

    @Override
    @NotNull
    public Collection<EventListener> getListeners() {
        return listeners;
    }
}
