package com.odeyalo.sonata.harmony.service.event;

import com.odeyalo.sonata.harmony.entity.AlbumReleaseEntity;
import com.odeyalo.sonata.harmony.repository.memory.InMemoryAlbumReleaseRepository;
import com.odeyalo.sonata.suite.brokers.events.album.AlbumDurationResolvedEvent;
import com.odeyalo.sonata.suite.brokers.events.album.data.AlbumDurationResolvedPayload;
import com.odeyalo.sonata.suite.brokers.events.album.data.TrackDuration;
import com.odeyalo.sonata.suite.brokers.events.album.data.TrackDurationContainer;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;
import testing.events.MockSonataEvent;
import testing.faker.AlbumReleaseEntityFaker;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


class AlbumDurationUpdatingEventListenerTest {

    @Test
    void shouldReturnTrueForSupportingEventType() {
        AlbumDurationUpdatingEventListener testable = new AlbumDurationUpdatingEventListener(new InMemoryAlbumReleaseRepository());

        boolean res = testable.supports(new AlbumDurationResolvedEvent(new AlbumDurationResolvedPayload("1", 123, emptyContainer())));

        assertThat(res).isTrue();
    }

    @Test
    void shouldReturnFalseForNotSupportingEventType() {
        AlbumDurationUpdatingEventListener testable = new AlbumDurationUpdatingEventListener(new InMemoryAlbumReleaseRepository());

        boolean res = testable.supports(new MockSonataEvent("hello"));

        assertThat(res).isFalse();
    }

    @Test
    void shouldUpdateExistingAlbumWithTotalDurationFromPayload() {
        String albumId = "123";
        InMemoryAlbumReleaseRepository albumReleaseRepository = new InMemoryAlbumReleaseRepository();
        AlbumReleaseEntity albumEntity = AlbumReleaseEntityFaker.create().id(123).get();
        albumReleaseRepository.save(albumEntity).block();

        AlbumDurationUpdatingEventListener testable = new AlbumDurationUpdatingEventListener(albumReleaseRepository);
        AlbumDurationResolvedEvent event = createEvent(albumId);

        testable.handleEvent(event).block();

        albumReleaseRepository.findById(123L)
                .as(StepVerifier::create)
                .expectNextMatches(entity -> entity.getDurationMs() == event.getBody().getTotalDurationMs())
                .verifyComplete();
    }

    @NotNull
    private static AlbumDurationResolvedEvent createEvent(String albumId) {
        TrackDuration firstDuration = TrackDuration.builder().trackId("track1").durationMs(89241L).build();

        AlbumDurationResolvedPayload payload = AlbumDurationResolvedPayload.builder()
                .albumId(albumId)
                .totalDurationMs(89241L)
                .trackDurationContainer(
                        TrackDurationContainer.fromCollection(List.of(firstDuration))
                )
                .build();
        return new AlbumDurationResolvedEvent(payload);
    }

    private static TrackDurationContainer emptyContainer() {
        return TrackDurationContainer.fromCollection(new ArrayList<>());
    }
}