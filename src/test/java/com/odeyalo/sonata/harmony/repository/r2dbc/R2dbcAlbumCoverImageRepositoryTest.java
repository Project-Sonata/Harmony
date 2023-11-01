package com.odeyalo.sonata.harmony.repository.r2dbc;

import com.odeyalo.sonata.harmony.entity.AlbumCoverImageEntity;
import com.odeyalo.sonata.harmony.entity.AlbumReleaseEntity;
import com.odeyalo.sonata.harmony.entity.ArtistContainerEntity;
import com.odeyalo.sonata.harmony.entity.TrackContainerEntity;
import com.odeyalo.sonata.harmony.model.AlbumType;
import com.odeyalo.sonata.harmony.repository.r2dbc.delegate.R2dbcAlbumCoverImageRepositoryDelegate;
import com.odeyalo.sonata.harmony.repository.r2dbc.delegate.R2dbcAlbumReleaseRepositoryDelegate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.List;

import static com.odeyalo.sonata.harmony.entity.AlbumReleaseEntity.builder;
import static com.odeyalo.sonata.harmony.repository.r2dbc.R2dbcAlbumCoverImageRepositoryTest.Configuration;
import static org.assertj.core.api.Assertions.assertThat;

@DataR2dbcTest
@TestInstance(Lifecycle.PER_CLASS)
@ActiveProfiles("test")
@Import(Configuration.class)
class R2dbcAlbumCoverImageRepositoryTest {

    @Autowired
    R2dbcAlbumCoverImageRepository testable;

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
    void shouldReturnEmptyMonoIfImageNotExistByAlbumId() {
        testable.findByAlbumId(-1L)
                .as(StepVerifier::create)
                .verifyComplete();
    }

    @Test
    void shouldFindByAlbumId() {
        var expected = createAlbumCoverImageEntity();

        insertImageEntities(expected);

        testable.findByAlbumId(expected.getAlbumId())
                .as(StepVerifier::create)
                .expectNext(expected)
                .verifyComplete();
    }

    @Test
    void shouldGenerateIdWhenSave() {
        var expected = createAlbumCoverImageEntity();

        testable.save(expected)
                .as(StepVerifier::create)
                .expectNextMatches(actual -> actual.getId() != null)
                .verifyComplete();
    }

    @Test
    void shouldFindById() {
        var expected = createAlbumCoverImageEntity();

        insertImageEntities(expected);

        testable.findById(expected.getId())
                .as(StepVerifier::create)
                .expectNext(expected)
                .verifyComplete();
    }

    @Test
    void shouldReturnEmptyMonoIfByIdNotExist() {
        testable.findById(-1L)
                .as(StepVerifier::create)
                .verifyComplete();
    }

    @Test
    void saveAllWithList() {
        var expected1 = createAlbumCoverImageEntity();
        var expected2 = createAlbumCoverImageEntity2();

        List<AlbumCoverImageEntity> savedImages = testable.saveAll(List.of(expected1, expected2)).collectList().block();

        assertThat(savedImages).containsAll(List.of(expected1, expected2));

    }

    @Test
    void saveAllWithFlux() {
        var expected1 = createAlbumCoverImageEntity();
        var expected2 = createAlbumCoverImageEntity2();

        List<AlbumCoverImageEntity> savedImages = testable.saveAll(Flux.just(expected1, expected2)).collectList().block();

        assertThat(savedImages).containsAll(List.of(expected1, expected2));
    }

    @Test
    void shouldReturnEmptyFluxIfNothingFound() {
        testable.findAll()
                .as(StepVerifier::create)
                .verifyComplete();
    }

    @Test
    void shouldReturnFluxWithEntities() {
        var expected1 = createAlbumCoverImageEntity();
        var expected2 = createAlbumCoverImageEntity2();

        insertImageEntities(expected1, expected2);

        List<AlbumCoverImageEntity> savedImages = testable.findAll().collectList().block();

        assertThat(savedImages).containsAll(List.of(expected1, expected2));
    }

    @Test
    void shouldReturnEmptyFluxIfFindAllFoundNothing() {
        testable.findAllById(Flux.just(-1L, -2L))
                .as(StepVerifier::create)
                .verifyComplete();
    }

    @Test
    void shouldReturnEverythingForExistingIds() {
        var expected1 = createAlbumCoverImageEntity();
        var expected2 = createAlbumCoverImageEntity2();

        insertImageEntities(expected1, expected2);

        List<AlbumCoverImageEntity> foundImages = testable.findAllById(List.of(expected1.getId(), expected2.getId())).collectList().block();

        assertThat(foundImages).containsAll(List.of(expected1, expected2));
    }

    @Test
    void shouldReturnOnlyFoundEntities() {
        var expected1 = createAlbumCoverImageEntity();

        insertImageEntities(expected1);

        testable.findAllById(List.of(expected1.getId(), -1L))
                .as(StepVerifier::create)
                .expectNext(expected1)
                .verifyComplete();

    }

    @Test
    void deleteById() {
        var expected1 = createAlbumCoverImageEntity();
        var expected2 = createAlbumCoverImageEntity2();

        insertImageEntities(expected1, expected2);

        testable.deleteById(expected1.getId())
                .as(StepVerifier::create)
                .verifyComplete();

        testable.findById(expected1.getId())
                .as(StepVerifier::create)
                .verifyComplete();
    }

    @Test
    void deleteAll() {
        var expected1 = createAlbumCoverImageEntity();
        var expected2 = createAlbumCoverImageEntity2();

        insertImageEntities(expected1, expected2);

        testable.deleteAll()
                .as(StepVerifier::create)
                .verifyComplete();

        testable.findById(expected1.getId())
                .as(StepVerifier::create)
                .verifyComplete();

        testable.findById(expected1.getId())
                .as(StepVerifier::create)
                .verifyComplete();
    }

    private void insertImageEntities(AlbumCoverImageEntity... entities) {
        testable.saveAll(List.of(entities))
                .as(StepVerifier::create)
                .expectNextCount(entities.length)
                .verifyComplete();
    }

    private static AlbumCoverImageEntity createAlbumCoverImageEntity() {
        return AlbumCoverImageEntity.builder()
                .albumId(existingAlbum.getId())
                .url("https://cdn.sonata.com/i/imageid")
                .width(300)
                .height(300)
                .build();
    }

    private static AlbumCoverImageEntity createAlbumCoverImageEntity2() {
        return AlbumCoverImageEntity.builder()
                .albumId(existingAlbum.getId())
                .url("https://cdn.sonata.com/i/mikuu")
                .width(300)
                .height(300)
                .build();
    }

    @TestConfiguration
    static class Configuration {

        @Bean
        public R2dbcAlbumCoverImageRepository r2dbcAlbumCoverImageRepository(R2dbcAlbumCoverImageRepositoryDelegate delegate) {
            return new R2dbcAlbumCoverImageRepository(delegate);
        }
    }
}