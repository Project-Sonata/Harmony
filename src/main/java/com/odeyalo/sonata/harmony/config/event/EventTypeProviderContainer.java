package com.odeyalo.sonata.harmony.config.event;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

/**
 * Immutable container for event type unique key and class representation as string
 */
public final class EventTypeProviderContainer {
    private final Map<String, String> eventTypeKeyClassName;

    private EventTypeProviderContainer(Map<String, String> eventTypeKeyClassName) {
        this.eventTypeKeyClassName = eventTypeKeyClassName;
    }

    public static EventTypeProviderContainer fromMap(@NotNull Map<String, String> eventTypeKeyClassName) {
        return new EventTypeProviderContainer(eventTypeKeyClassName);
    }

    public boolean contains(@NotNull String eventTypeName) {
        return eventTypeKeyClassName.containsKey(eventTypeName);
    }

    @Nullable
    public String getClassName(@NotNull String eventTypeName) {
        return eventTypeKeyClassName.get(eventTypeName);
    }
}
