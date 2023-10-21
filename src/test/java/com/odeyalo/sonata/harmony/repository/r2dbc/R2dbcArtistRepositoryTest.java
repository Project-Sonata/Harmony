package com.odeyalo.sonata.harmony.repository.r2dbc;

import com.odeyalo.sonata.harmony.entity.ArtistEntity;
import com.odeyalo.sonata.harmony.repository.r2dbc.delegate.R2dbcArtistRepositoryDelegate;
import org.junit.jupiter.api.AfterEach;
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
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@DataR2dbcTest
@TestInstance(Lifecycle.PER_CLASS)
@ActiveProfiles("test")
@Import(R2dbcArtistRepositoryTest.Configuration.class)
public class R2dbcArtistRepositoryTest {

    @Autowired
    R2dbcArtistRepository testable;

    @AfterEach
    void tearDown() {
        testable.deleteAll()
                .as(StepVerifier::create)
                .verifyComplete();
    }

    @Test
    void shouldGenerateArtistId() {
        var artist = generateArtistWithoutId();

        testable.save(artist)
                .as(StepVerifier::create)
                .expectNextMatches(actual -> actual.getId() != null)
                .verifyComplete();
    }

    @Test
    void shouldSaveArtistName() {
        var expected = generateArtistWithoutId();

        insertArtists(expected);

        testable.findById(expected.getId())
                .as(StepVerifier::create)
                .expectNextMatches(actual -> Objects.equals(expected.getName(), actual.getName()))
                .verifyComplete();
    }

    @Test
    void shouldSaveSonataId() {
        var expected = generateArtistWithoutId();

        insertArtists(expected);

        testable.findById(expected.getId())
                .as(StepVerifier::create)
                .expectNextMatches(actual -> Objects.equals(expected.getSonataId(), actual.getSonataId()))
                .verifyComplete();
    }

    @Test
    void shouldFindBySonataId() {
        var expected = generateArtistWithoutId();

        insertArtists(expected);

        testable.findBySonataId(expected.getSonataId())
                .as(StepVerifier::create)
                .expectNext(expected)
                .verifyComplete();
    }

    @Test
    void shouldReturnEmptyIfSonataIdDoesNotExist() {
        testable.findBySonataId("not_exist")
                .as(StepVerifier::create)
                .verifyComplete();
    }

    @Test
    void shouldDeleteAll() {
        var artist = generateArtistWithoutId();

        insertArtists(artist);

        testable.deleteAll()
                .as(StepVerifier::create)
                .verifyComplete();

        testable.findById(artist.getId())
                .as(StepVerifier::create)
                .verifyComplete();
    }

    @Test
    void shouldDeleteById() {
        var artist = generateArtistWithoutId();

        insertArtists(artist);

        testable.deleteById(artist.getId())
                .as(StepVerifier::create)
                .verifyComplete();

        testable.findById(artist.getId())
                .as(StepVerifier::create)
                .verifyComplete();
    }

    @Test
    void shouldNotDeleteAnythingIfIdDoesNotExist() {
        var artist = generateArtistWithoutId();

        insertArtists(artist);

        testable.deleteById(-1L)
                .as(StepVerifier::create)
                .verifyComplete();

        testable.findById(artist.getId())
                .as(StepVerifier::create)
                .expectNext(artist)
                .verifyComplete();
    }

    @Test
    void shouldFindById() {
        var artist = generateArtistWithoutId();

        insertArtists(artist);

        testable.findById(artist.getId())
                .as(StepVerifier::create)
                .expectNext(artist)
                .verifyComplete();
    }

    @Test
    void shouldReturnEmptyIfIdDoesNotExist() {
        testable.findById(-1L)
                .as(StepVerifier::create)
                .verifyComplete();
    }

    @Test
    void shouldFindAllById() {
        var artist1 = generateArtistWithoutId();
        var artist2 = sampleArtist2();

        insertArtists(artist1, artist2);

        List<ArtistEntity> foundArtists = testable
                .findAllById(Flux.just(artist1.getId(), artist2.getId()))
                .collectList().block();

        // Do not use the StepVerifier here because findAll* methods do not guarantee order
        assertThat(foundArtists).containsAll(List.of(artist1, artist2));
    }

    @Test
    void shouldReturnEmptyListIfNothingIsFound() {
        List<ArtistEntity> foundArtists = testable
                .findAllById(Flux.just(1L, 2L))
                .collectList().block();

        // Do not use the StepVerifier here because findAll* methods do not guarantee order
        assertThat(foundArtists).isEmpty();
    }

    @Test
    void shouldReturnListOnlyWithElementsThatWasFound() {
        var artist = generateArtistWithoutId();

        insertArtists(artist);

        List<ArtistEntity> foundArtists = testable
                .findAllById(Flux.just(-1L, artist.getId()))
                .collectList().block();

        // Do not use the StepVerifier here because findAll* methods do not guarantee order
        assertThat(foundArtists).contains(artist);
    }

    private void insertArtists(ArtistEntity... artists) {
        testable.saveAll(List.of(artists))
                .as(StepVerifier::create)
                .expectNextCount(artists.length)
                .verifyComplete();
    }

    private static ArtistEntity generateArtistWithoutId() {
        return ArtistEntity.builder().sonataId("something").name("Lil Peep").build();
    }

    private static ArtistEntity sampleArtist2() {
        return ArtistEntity.builder().sonataId("unique_id").name("bones").build();
    }

    @TestConfiguration
    public static class Configuration {

        @Bean
        public R2dbcArtistRepository r2dbcArtistRepository(R2dbcArtistRepositoryDelegate delegate) {
            return new R2dbcArtistRepository(delegate);
        }
    }
}
