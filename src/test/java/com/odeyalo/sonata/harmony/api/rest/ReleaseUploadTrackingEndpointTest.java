package com.odeyalo.sonata.harmony.api.rest;

import com.odeyalo.sonata.harmony.config.Converters;
import com.odeyalo.sonata.harmony.entity.AlbumReleaseEntity;
import com.odeyalo.sonata.harmony.entity.AlbumReleaseTrackingEntity;
import com.odeyalo.sonata.harmony.repository.AlbumReleaseTrackingRepository;
import com.odeyalo.sonata.harmony.repository.memory.InMemoryAlbumReleaseRepository;
import com.odeyalo.sonata.harmony.repository.memory.InMemoryAlbumReleaseTrackingRepository;
import com.odeyalo.sonata.harmony.service.album.tracking.DefaultAlbumReleaseUploadTrackingService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import reactor.test.StepVerifier;
import testing.faker.AlbumReleaseEntityFaker;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;


@TestInstance(Lifecycle.PER_CLASS)
class ReleaseUploadTrackingEndpointTest {

    public static final String EXISTING_TRACKING_ID = "miku!";

    AlbumReleaseTrackingRepository trackingRepository = new InMemoryAlbumReleaseTrackingRepository();
    InMemoryAlbumReleaseRepository albumReleaseRepository = new InMemoryAlbumReleaseRepository();

    ReleaseUploadTrackingController testable = new ReleaseUploadTrackingController(new DefaultAlbumReleaseUploadTrackingService(
            trackingRepository, albumReleaseRepository, new Converters().albumReleaseConverter()
    ));

    @BeforeEach
    void setUp() {
        AlbumReleaseEntity albumReleaseEntity = AlbumReleaseEntityFaker.create().id(1L).get();
        albumReleaseRepository.save(albumReleaseEntity).block();

        AlbumReleaseTrackingEntity entity = AlbumReleaseTrackingEntity.of(EXISTING_TRACKING_ID, 1L);

        trackingRepository.save(entity).block();
    }

    @AfterEach
    void tearDown() {
        trackingRepository.deleteAll().block();
    }

    @Test
    void shouldReturnOkStatusForExistingId() {
        testable.getCurrentStatus(EXISTING_TRACKING_ID)
                .as(StepVerifier::create)
                .expectNextMatches(response -> response.getStatusCode().isSameCodeAs(OK))
                .verifyComplete();
    }

    @Test
    void shouldReturnUnprocessableEntityStatusForExistingId() {
        testable.getCurrentStatus("not_exist")
                .as(StepVerifier::create)
                .expectNextMatches(response -> response.getStatusCode().isSameCodeAs(UNPROCESSABLE_ENTITY))
                .verifyComplete();
    }
}