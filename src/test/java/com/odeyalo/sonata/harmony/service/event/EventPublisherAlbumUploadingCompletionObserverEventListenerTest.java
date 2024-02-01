package com.odeyalo.sonata.harmony.service.event;

import com.odeyalo.sonata.harmony.config.Converters;
import com.odeyalo.sonata.harmony.repository.memory.InMemoryAlbumReleaseRepository;
import com.odeyalo.sonata.harmony.service.album.AlbumReleaseService;
import com.odeyalo.sonata.harmony.service.album.DefaultAlbumReleaseService;
import com.odeyalo.sonata.harmony.service.event.impl.AlbumUploadingFullyFinishedEventPublisher;
import com.odeyalo.sonata.harmony.support.converter.external.UploadedAlbumInfoDtoConverter;
import com.odeyalo.sonata.suite.brokers.events.SonataEvent;
import com.odeyalo.sonata.suite.brokers.events.album.*;
import com.odeyalo.sonata.suite.brokers.events.album.data.*;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.Mockito;
import testing.events.MockSonataEvent;
import testing.faker.AlbumReleaseEntityFaker;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;

class EventPublisherAlbumUploadingCompletionObserverEventListenerTest {

    public static final String ALBUM_ID = "12345";
    Supplier<AlbumUploadingFullyFinishedEventPublisher> eventPublisherSupplier = () -> new AlbumUploadingFullyFinishedEventPublisher(
            new InternalEventPublisherDelegate(new SinkEmittingEventSource<>())
    );

    Supplier<AlbumReleaseService> albumReleaseServiceSupplier = () -> new DefaultAlbumReleaseService(
            new InMemoryAlbumReleaseRepository(), new Converters().albumReleaseConverter()
    );

    Supplier<UploadedAlbumInfoDtoConverter> uploadAlbumReleaseInfoConverterSupplier = () ->
            new Converters().uploadedAlbumInfoDtoConverter();


    @Test
    void shouldNotSendEventWhenProvidedEventsHaveNotBeenOccurred() {
        AlbumUploadingFullyFinishedEventPublisher eventPublisher = Mockito.spy(eventPublisherSupplier.get());

        EventPublisherAlbumUploadingCompletionObserverEventListener testable = new EventPublisherAlbumUploadingCompletionObserverEventListener(
                List.of(BasicAlbumInfoUploadedEvent.EVENT_TYPE, AlbumDurationResolvedEvent.EVENT_TYPE, Mp3TrackPreviewGeneratedEvent.EVENT_TYPE),
                eventPublisher,
                albumReleaseServiceSupplier.get(),
                uploadAlbumReleaseInfoConverterSupplier.get()
        );

        testable.handleEvent(new MockAlbumUploadingEvent()).block();

        // check that outgoing event has been sent successfully
        Mockito.verify(eventPublisher, Mockito.times(0)).publishEvent(any());
    }

    @Test
    void shouldSendEventWhenProvidedEventsHaveBeenOccurred() {
        AlbumUploadingFullyFinishedEventPublisher eventPublisher = Mockito.spy(eventPublisherSupplier.get());
        InMemoryAlbumReleaseRepository albumReleaseRepository = new InMemoryAlbumReleaseRepository();
        albumReleaseRepository.save(AlbumReleaseEntityFaker.create().id(Long.parseLong(ALBUM_ID)).get()).block();

        DefaultAlbumReleaseService albumReleaseService = new DefaultAlbumReleaseService(
                albumReleaseRepository, new Converters().albumReleaseConverter()
        );

        EventPublisherAlbumUploadingCompletionObserverEventListener testable = new EventPublisherAlbumUploadingCompletionObserverEventListener(
                List.of(
                        BasicAlbumInfoUploadedEvent.EVENT_TYPE,
                        AlbumDurationResolvedEvent.EVENT_TYPE,
                        Mp3TrackPreviewGeneratedEvent.EVENT_TYPE
                ), eventPublisher,
                albumReleaseService,
                uploadAlbumReleaseInfoConverterSupplier.get()
        );
        BasicAlbumInfoUploadedPayload basicAlbumInfoUploadedPayload = getBasicAlbumInfoUploadedPayload();
        // Send first event
        testable.handleEvent(new BasicAlbumInfoUploadedEvent(basicAlbumInfoUploadedPayload)).block();

        Mp3TrackPreviewGeneratedEvent mp3TrackPreviewGeneratedEvent = new Mp3TrackPreviewGeneratedEvent(
                new Mp3TrackPreviewGeneratedPayload("213", ALBUM_ID, "http://localhost:3000/previews/track.mp3")
        );
        //send second event
        testable.handleEvent(mp3TrackPreviewGeneratedEvent).block();

        // third event, final event, after that new event should be fired using AlbumUploadingFullyFinishedEventPublisher
        testable.handleEvent(new AlbumDurationResolvedEvent(getAlbumDurationResolvedPayload())).block();

        // check that outgoing event has been sent successfully
        Mockito.verify(eventPublisher, Mockito.times(1))
                .publishEvent(argThat(albumUploadingFullyFinishedEventArgumentMatcher()));
    }

    @Test
    void shouldReturnTrueForSupportedEvent() {
        EventPublisherAlbumUploadingCompletionObserverEventListener testable = new EventPublisherAlbumUploadingCompletionObserverEventListener(Collections.emptyList(),
                eventPublisherSupplier.get(),
                albumReleaseServiceSupplier.get(),
                uploadAlbumReleaseInfoConverterSupplier.get()
        );

        boolean res = testable.supports(new MockAlbumUploadingEvent());

        assertThat(res).isTrue();
    }

    @Test
    void shouldReturnFalseForUnsupportedEvent() {
        EventPublisherAlbumUploadingCompletionObserverEventListener testable = new EventPublisherAlbumUploadingCompletionObserverEventListener(Collections.emptyList(),
                eventPublisherSupplier.get(),
                albumReleaseServiceSupplier.get(),
                uploadAlbumReleaseInfoConverterSupplier.get()
        );

        boolean res = testable.supports(new MockSonataEvent("body"));

        assertThat(res).isFalse();
    }

    @SuppressWarnings("InnerClassMayBeStatic")
    private final class MockAlbumUploadingEvent implements SonataEvent, AlbumUploadingEvent {
        public static final String EVENT_TYPE = "mock_event";

        @Override
        public String id() {
            return "213";
        }

        @Override
        public long creationTime() {
            return System.currentTimeMillis();
        }

        @Override
        public @NotNull String getAlbumId() {
            return ALBUM_ID;
        }

        @Override
        public @NotNull String getEventType() {
            return EVENT_TYPE;
        }
    }

    @NotNull
    private static ArgumentMatcher<AlbumUploadingFullyFinishedEvent> albumUploadingFullyFinishedEventArgumentMatcher() {
        return event -> Objects.equals(event.getBody().getAlbumInfo().getId(), ALBUM_ID);
    }

    @NotNull
    private static BasicAlbumInfoUploadedPayload getBasicAlbumInfoUploadedPayload() {
        ArtistContainerDto artist = ArtistContainerDto.single(ArtistDto.of("123", "Salvia Palth"));
        return BasicAlbumInfoUploadedPayload.builder()
                .id(ALBUM_ID)
                .albumType(AlbumType.SINGLE)
                .artists(artist)
                .albumName("Melanchole")
                .coverImage(CoverImage.builder().uri(URI.create("http::/localhost:3000/i/image.png")).build())
                .trackCount(1)
                .releaseDateAsString("2024")
                .releaseDatePrecision("YEAR")
                .uploadedTracks(
                        UploadedTrackSimplifiedInfoContainerDto.fromArray(
                                UploadedTrackSimplifiedInfoDto.builder()
                                        .uri(URI.create("http::/localhost:3000/i/image.png"))
                                        .name("something")
                                        .artists(artist)
                                        .id("123")
                                        .build()
                        )
                )
                .build();
    }

    @NotNull
    private static AlbumDurationResolvedPayload getAlbumDurationResolvedPayload() {
        return new AlbumDurationResolvedPayload(ALBUM_ID, 321421L, TrackDurationContainer.fromCollection(
                Collections.singletonList(new TrackDuration("1", 321421L))
        ));
    }
}