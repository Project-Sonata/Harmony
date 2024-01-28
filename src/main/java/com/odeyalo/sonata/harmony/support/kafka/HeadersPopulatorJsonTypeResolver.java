package com.odeyalo.sonata.harmony.support.kafka;

import com.fasterxml.jackson.databind.JavaType;
import com.odeyalo.sonata.harmony.config.event.EventTypeProviderContainer;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.Headers;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.kafka.support.mapping.AbstractJavaTypeMapper;
import org.springframework.kafka.support.mapping.Jackson2JavaTypeMapper;
import org.springframework.kafka.support.serializer.JsonTypeResolver;

import static org.springframework.kafka.support.mapping.AbstractJavaTypeMapper.DEFAULT_CLASSID_FIELD_NAME;

/**
 * Populate the headers with {@link AbstractJavaTypeMapper#DEFAULT_CLASSID_FIELD_NAME}, if missing,
 * by resolving the class name using {@link EventTypeProviderContainer}
 */
public class HeadersPopulatorJsonTypeResolver implements JsonTypeResolver {
    private final Jackson2JavaTypeMapper typeMapper;
    private final EventTypeProviderContainer eventTypeProviderContainer;

    public static final String EVENT_TYPE_HEADER_NAME = "event_type";

    public HeadersPopulatorJsonTypeResolver(Jackson2JavaTypeMapper typeMapper, EventTypeProviderContainer eventTypeProviderContainer) {
        this.typeMapper = typeMapper;
        this.eventTypeProviderContainer = eventTypeProviderContainer;
    }

    @Override
    @Nullable
    public JavaType resolveType(@NotNull String topic,
                                byte[] data,
                                @NotNull Headers headers) {

        if ( headers.lastHeader(DEFAULT_CLASSID_FIELD_NAME) != null ) {
            return typeMapper.toJavaType((headers));
        }

        Header eventType = headers.lastHeader(EVENT_TYPE_HEADER_NAME);

        if ( eventType == null ) {
            // We don't have event type in headers, skip it and hope for another resolver to resolve it
            return null;
        }

        String eventTypeHeaderValue = new String(eventType.value());

        if ( eventTypeProviderContainer.contains(eventTypeHeaderValue) ) {
            String className = eventTypeProviderContainer.getClassName(eventTypeHeaderValue);
            //noinspection DataFlowIssue
            headers.add(DEFAULT_CLASSID_FIELD_NAME, className.getBytes());
        }
        // Delegate resolving of JavaType to typeMapper
        return typeMapper.toJavaType(headers);
    }
}
