package com.odeyalo.sonata.harmony.service.album.tracking;

import com.odeyalo.sonata.harmony.config.Converters;
import com.odeyalo.sonata.harmony.repository.memory.InMemoryAlbumReleaseRepository;
import com.odeyalo.sonata.harmony.repository.memory.InMemoryAlbumReleaseTrackingRepository;
import com.odeyalo.sonata.harmony.support.converter.AlbumReleaseConverter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;
import testing.faker.AlbumReleaseEntityFaker;
import testing.faker.AlbumReleaseFaker;

import java.util.Objects;

import static com.odeyalo.sonata.harmony.service.album.tracking.TrackingStatus.VERIFICATION_REQUIRED;
import static org.assertj.core.api.Assertions.assertThat;

class DefaultAlbumReleaseUploadTrackingServiceTest {
    InMemoryAlbumReleaseTrackingRepository trackingRepository = new InMemoryAlbumReleaseTrackingRepository();
    InMemoryAlbumReleaseRepository albumReleaseRepository = new InMemoryAlbumReleaseRepository();
    AlbumReleaseConverter albumReleaseConverter = new Converters().albumReleaseConverter();

    DefaultAlbumReleaseUploadTrackingService testable = new DefaultAlbumReleaseUploadTrackingService(
            trackingRepository,
            albumReleaseRepository,
            albumReleaseConverter
    );

    @AfterEach
    void clear() {
        trackingRepository.deleteAll()
                .then(albumReleaseRepository.deleteAll())
                .block();
    }

    @Test
    void shouldReturnExceptionOnNullAlbumReleaseId() {
        var albumRelease = AlbumReleaseFaker.create().get();

        testable.startTracking(albumRelease, VERIFICATION_REQUIRED)
                .as(StepVerifier::create)
                .expectError(IllegalStateException.class)
                .verify();
    }

    @Test
    void startTrackingWithVerificationRequiredStatusShouldReturnValue() {
        var albumRelease = AlbumReleaseFaker.create().id(10L).get();

        testable.startTracking(albumRelease, VERIFICATION_REQUIRED)
                .as(StepVerifier::create)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    void shouldSaveToRepositoryOnStartTracking() {
        var albumRelease = AlbumReleaseFaker.create().id(10L).get();

        TrackingInfo result = testable.startTracking(albumRelease, VERIFICATION_REQUIRED).block();

        assertThat(result).isNotNull();

        trackingRepository.findByTrackingId(result.getTrackingId())
                .as(StepVerifier::create)
                .expectNextMatches(actual -> Objects.equals(actual.getAlbumId(), albumRelease.getId()))
                .verifyComplete();
    }

    @Test
    void shouldFindByExistingId() {
        var albumReleaseEntity = AlbumReleaseEntityFaker.create().id(10).get();
        var albumRelease = albumReleaseConverter.toAlbumRelease(albumReleaseEntity);

        albumReleaseRepository.save(albumReleaseEntity).block();


        TrackingInfo result = testable.startTracking(albumRelease, VERIFICATION_REQUIRED).block();

        assertThat(result).isNotNull();

        testable.findByTrackingId(result.getTrackingId())
                .as(StepVerifier::create)
                .expectNextMatches(actual -> Objects.equals(actual, albumRelease))
                .verifyComplete();
    }

    @Test
    void shouldReturnEmptyOnNonExisting() {
        testable.findByTrackingId("not_existing")
                .as(StepVerifier::create)
                .verifyComplete();
    }
}