package com.odeyalo.sonata.harmony.repository.r2dbc.delegate;

import com.odeyalo.sonata.harmony.entity.AlbumCoverImageEntity;
import com.odeyalo.sonata.harmony.entity.AlbumReleaseEntity;
import com.odeyalo.sonata.harmony.entity.ArtistContainerEntity;
import com.odeyalo.sonata.harmony.entity.TrackContainerEntity;
import com.odeyalo.sonata.harmony.model.AlbumType;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.test.context.ActiveProfiles;
import reactor.test.StepVerifier;

import java.util.List;

import static com.odeyalo.sonata.harmony.entity.AlbumReleaseEntity.builder;

@DataR2dbcTest
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
class R2dbcAlbumCoverImageRepositoryDelegateTest {

    @Autowired
    R2dbcAlbumCoverImageRepositoryDelegate testable;

    @Autowired
    R2dbcAlbumReleaseRepositoryDelegate r2dbcAlbumReleaseRepository;

    static AlbumReleaseEntity existingAlbum;

    @BeforeEach
    void setUp() {
        existingAlbum = builder()
                .albumName("dudeness")
                .albumType(AlbumType.SINGLE)
                .durationMs(1000L)
                .totalTracksCount(2)
                .artists(ArtistContainerEntity.empty())
                .tracks(TrackContainerEntity.empty())
                .releaseDateAsString("2023")
                .releaseDatePrecision("YEAR")
                .build();

        r2dbcAlbumReleaseRepository.save(existingAlbum)
                .as(StepVerifier::create)
                .expectNextCount(1)
                .verifyComplete();
    }

    @AfterEach
    void clear() {
        testable.deleteAll()
                .then(r2dbcAlbumReleaseRepository.deleteAll())
                .block();
    }

    @Test
    void shouldFindByAlbumId() {
        var expected = createAlbumCoverImageEntity();

        insertImageEntities(expected);

        testable.findAllByAlbumId(existingAlbum.getId())
                .as(StepVerifier::create)
                .expectNext(expected)
                .verifyComplete();
    }

    @Test
    void shouldReturnEmptyFlux() {
        testable.findAllByAlbumId(-1L)
                .as(StepVerifier::create)
                .verifyComplete();
    }

    private void insertImageEntities(AlbumCoverImageEntity... entities) {
        testable.saveAll(List.of(entities))
                .as(StepVerifier::create)
                .expectNextCount(entities.length)
                .verifyComplete();
    }

    @NotNull
    private static AlbumCoverImageEntity createAlbumCoverImageEntity() {
        return AlbumCoverImageEntity.builder()
                .albumId(existingAlbum.getId())
                .url("https://cdn.sonata.com/i/imageid")
                .width(300)
                .height(300)
                .build();
    }
}