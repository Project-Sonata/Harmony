package com.odeyalo.sonata.harmony.service.event;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * Registry for event listeners. Used to registry the event listeners
 * and as single-place store for EventListener(s).
 * <p>
 * Optionally can invoke the methods, like {@link ListenerInvocationCapableEventManager}
 */
public interface EventListenerRegistry {
    /**
     * Add the given listener to registry.
     *
     * @param eventListener - listener to add to the registry. Never null
     */
    void addListener(@NotNull EventListener eventListener);

    /**
     * Remove the given listener from the registry.
     * If the listener does not present in regisry, then do nothing
     *
     * @param eventListener - listener to delete from, not null.
     */
    void deleteListener(@NotNull EventListener eventListener);

    /**
     * Return collection of all listeners.
     *
     * @return mutable collection of all listeners
     */
    @NotNull
    Collection<EventListener> getListeners();

}
