package com.odeyalo.sonata.harmony.config.event;

import com.odeyalo.sonata.suite.brokers.events.EventTypeProvider;
import lombok.SneakyThrows;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.*;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.*;

@Configuration
public class EventTypeConfiguration {

    public static final String EVENT_TYPE_FIELD_NAME = "EVENT_TYPE";
    private static final String EVENT_TYPE_FIELD_MISSING_EXCEPTION_MESSAGE = "public static final String EVENT_TYPE field name in EventTypeProvider implementation is required. " +
            "Please, set the field with unique identifier as EVENT_TYPE field value";

    @Bean
    @SneakyThrows
    public EventTypeProviderContainer eventTypeProviderContainer() {
        var classpathScanner = new ClassPathScanningCandidateComponentProvider(false);
        classpathScanner.addIncludeFilter(new AssignableTypeFilter(EventTypeProvider.class));
        Set<BeanDefinition> definitions = classpathScanner.findCandidateComponents("com.odeyalo.sonata");

        Map<String, String> eventTypeProviders = new HashMap<>();

        for (BeanDefinition definition : definitions) {
            String className = definition.getBeanClassName();
            Class<?> clazz = Class.forName(className);
            // Require the static field name with event type name,
            // because we can't access EventTypeProvider#getEventType() method without creating an instance
            Field eventType = ReflectionUtils.findField(clazz, EVENT_TYPE_FIELD_NAME);

            if ( eventType == null ) {
                throw new IllegalStateException(EVENT_TYPE_FIELD_MISSING_EXCEPTION_MESSAGE);
            }

            String eventTypeValue = (String) eventType.get(null);

            eventTypeProviders.put(eventTypeValue, className);
        }

        return EventTypeProviderContainer.fromMap(eventTypeProviders);
    }
}
