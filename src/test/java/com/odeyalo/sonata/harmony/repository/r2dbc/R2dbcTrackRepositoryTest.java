package com.odeyalo.sonata.harmony.repository.r2dbc;

import com.odeyalo.sonata.harmony.entity.ArtistContainerEntity;
import com.odeyalo.sonata.harmony.entity.ArtistEntity;
import com.odeyalo.sonata.harmony.entity.SimplifiedAlbumEntity;
import com.odeyalo.sonata.harmony.entity.TrackEntity;
import com.odeyalo.sonata.harmony.model.AlbumType;
import com.odeyalo.sonata.harmony.model.ReleaseDate;
import com.odeyalo.sonata.harmony.repository.SimplifiedAlbumRepository;
import com.odeyalo.sonata.harmony.repository.SimplifiedTrackRepository;
import com.odeyalo.sonata.harmony.repository.r2dbc.delegate.R2dbcSimplifiedAlbumRepositoryDelegate;
import com.odeyalo.sonata.harmony.repository.r2dbc.delegate.R2dbcSimplifiedTrackRepositoryDelegate;
import com.odeyalo.sonata.harmony.repository.r2dbc.delegate.R2dbcTrackRepositoryDelegate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import reactor.test.StepVerifier;
import testing.spring.R2dbcEntityCallbackConfiguration;

import java.util.List;
import java.util.Objects;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

@DataR2dbcTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
@Import(value = {
        R2dbcTrackRepositoryTest.Configuration.class,
        R2dbcEntityCallbackConfiguration.class
})
public class R2dbcTrackRepositoryTest {

    @Autowired
    R2dbcTrackRepository testable;

    @Autowired
    R2dbcSimplifiedAlbumRepositoryDelegate albumReleaseRepository;

    private static SimplifiedAlbumEntity existingAlbum;

    @BeforeEach
    void setUp() {
        SimplifiedAlbumEntity album = SimplifiedAlbumEntity.builder()
                .albumName("Berry is on top")
                .totalTracksCount(2)
                .releaseDate(ReleaseDate.onlyYear(2023))
                .albumType(AlbumType.EPISODE)
                .durationMs(4000523L)
                .artists(ArtistContainerEntity.solo(ArtistEntity.builder()
                        .sonataId("hello")
                        .name("Peep")
                        .build()))
                .build();
        existingAlbum = albumReleaseRepository.save(album).block();
    }

    @AfterEach
    void tearDown() {
        testable.deleteAll()
                .then(albumReleaseRepository.deleteAll()).block();
    }

    @Test
    void shouldGenerateId() {
        var track = generateTrackWithoutId();

        testable.save(track)
                .as(StepVerifier::create)
                .expectNextMatches(actual -> actual.getId() != null)
                .verifyComplete();
    }

    @Test
    void shouldSaveName() {
        var expected = generateTrackWithoutId();

        insertTracks(expected);

        testable.findById(expected.getId())
                .as(StepVerifier::create)
                .expectNextMatches(actual -> Objects.equals(expected.getName(), actual.getName()))
                .verifyComplete();
    }

    @Test
    void shouldSaveDurationMs() {
        var expected = generateTrackWithoutId();

        insertTracks(expected);

        testable.findById(expected.getId())
                .as(StepVerifier::create)
                .expectNextMatches(actual -> Objects.equals(expected.getDurationMs(), actual.getDurationMs()))
                .verifyComplete();
    }

    @Test
    void shouldSaveExplicitColumn() {
        var expected = generateTrackWithoutId();

        insertTracks(expected);

        testable.findById(expected.getId())
                .as(StepVerifier::create)
                .expectNextMatches(actual -> Objects.equals(expected.isExplicit(), actual.isExplicit()))
                .verifyComplete();
    }

    @Test
    void shouldSaveHasLyricsColumn() {
        var expected = generateTrackWithoutId();

        insertTracks(expected);

        testable.findById(expected.getId())
                .as(StepVerifier::create)
                .expectNextMatches(actual -> Objects.equals(expected.hasLyrics(), actual.hasLyrics()))
                .verifyComplete();
    }

    @Test
    void shouldSaveDiscNumber() {
        var expected = generateTrackWithoutId();

        insertTracks(expected);

        testable.findById(expected.getId())
                .as(StepVerifier::create)
                .expectNextMatches(actual -> Objects.equals(expected.getDiscNumber(), actual.getDiscNumber()))
                .verifyComplete();
    }

    @Test
    void shouldSaveTrackIndex() {
        var expected = generateTrackWithoutId();

        insertTracks(expected);

        testable.findById(expected.getId())
                .as(StepVerifier::create)
                .expectNextMatches(actual -> Objects.equals(expected.getIndex(), actual.getIndex()))
                .verifyComplete();
    }

    @Test
    void shouldSaveAlbumId() {
        var expected = generateTrackWithoutId();

        insertTracks(expected);

        testable.findById(expected.getId())
                .as(StepVerifier::create)
                .expectNextMatches(actual -> Objects.equals(expected.getAlbumId(), actual.getAlbumId()))
                .verifyComplete();
    }

    @Test
    void shouldSaveArtists() {
        var expected = generateTrackWithoutId();

        insertTracks(expected);

        testable.findById(expected.getId())
                .as(StepVerifier::create)
                .expectNextMatches(actual -> actual.getArtists() != null)
                .verifyComplete();
    }

    @Test
    void shouldSaveIfDurationMsIsNull() {
        var expected = generateTrackWithoutId();

        expected.setDurationMs(null);

        insertTracks(expected);

        testable.findById(expected.getId())
                .as(StepVerifier::create)
                .expectNext(expected)
                .verifyComplete();
    }

    @Test
    void shouldReturnAlbum() {
        var expected = generateTrackWithoutId();

        insertTracks(expected);

        testable.findById(expected.getId())
                .as(StepVerifier::create)
                .expectNextMatches(actual -> Objects.equals(expected.getAlbum(), actual.getAlbum()))
                .verifyComplete();
    }

    @Test
    void shouldFindAllByAlbumId() {
        var expected = generateTrackWithoutId();

        insertTracks(expected);

        testable.findAllByAlbumId(existingAlbum.getId())
                .as(StepVerifier::create)
                .expectNext(expected)
                .verifyComplete();
    }

    @Test
    void shouldReturnEmptyFluxIfAlbumIdNotExist() {
        testable.findAllByAlbumId(-1L)
                .as(StepVerifier::create)
                .verifyComplete();
    }

    @Test
    void shouldDeleteAll() {
        var expected = generateTrackWithoutId();

        insertTracks(expected);

        testable.deleteAll()
                .as(StepVerifier::create)
                .verifyComplete();

        testable.findById(expected.getId())
                .as(StepVerifier::create)
                .verifyComplete();
    }

    @Test
    void shouldDeleteByIdIfExist() {
        var expected = generateTrackWithoutId();

        insertTracks(expected);

        testable.deleteById(expected.getId())
                .as(StepVerifier::create)
                .verifyComplete();

        testable.findById(expected.getId())
                .as(StepVerifier::create)
                .verifyComplete();
    }

    @Test
    void shouldNotDeleteByIdIfNotExist() {
        var expected = generateTrackWithoutId();

        insertTracks(expected);

        testable.deleteById(-1L)
                .as(StepVerifier::create)
                .verifyComplete();

        testable.findById(expected.getId())
                .as(StepVerifier::create)
                .expectNext(expected)
                .verifyComplete();
    }

    @Test
    void shouldFindAllById() {
        var firstTrack = generateTrackWithoutId();
        var secondTrack = sampleTrack2();

        insertTracks(firstTrack, secondTrack);

        List<TrackEntity> actual = testable.findAllById(List.of(firstTrack.getId(), secondTrack.getId())).collectList().block();

        assertThat(actual).containsAll(List.of(firstTrack, secondTrack));
    }

    @Test
    void shouldFindOnlyExisting() {
        var firstTrack = generateTrackWithoutId();

        insertTracks(firstTrack);

        List<TrackEntity> actual = testable.findAllById(List.of(firstTrack.getId(), -1L)).collectList().block();

        assertThat(actual).containsAll(singletonList(firstTrack));
    }

    @Test
    void shouldReturnEmptyListIfNothingIsFound() {
        List<TrackEntity> actual = testable.findAllById(List.of(-1L, -2L)).collectList().block();

        assertThat(actual).isEmpty();
    }

    private void insertTracks(TrackEntity... entities) {
        testable.saveAll(List.of(entities))
                .as(StepVerifier::create)
                .expectNextCount(entities.length)
                .verifyComplete();
    }

    private static TrackEntity generateTrackWithoutId() {
        return TrackEntity.builder()
                .name("nothing")
                .explicit(true)
                .hasLyrics(true)
                .discNumber(1)
                .index(0)
                .albumId(existingAlbum.getId())
                .album(existingAlbum)
                .artists(existingAlbum.getArtists())
                .trackUrl("https://cdn.sonata.com/m/song1")
                .build();
    }

    private static TrackEntity sampleTrack2() {
        return TrackEntity.builder()
                .name("evening")
                .durationMs(1020L)
                .explicit(false)
                .hasLyrics(true)
                .discNumber(1)
                .index(1)
                .albumId(existingAlbum.getId())
                .album(existingAlbum)
                .artists(existingAlbum.getArtists())
                .trackUrl("https://cdn.sonata.com/m/song2")
                .build();
    }

    @TestConfiguration
    static class Configuration {

        @Bean
        public R2dbcTrackRepository r2dbcTrackRepository(R2dbcTrackRepositoryDelegate delegate) {
            return new R2dbcTrackRepository(delegate);
        }

        @Bean
        public SimplifiedTrackRepository simplifiedTrackRepository(R2dbcSimplifiedTrackRepositoryDelegate delegate) {
            return new R2dbcSimplifiedTrackRepository(delegate);
        }

        @Bean
        public SimplifiedAlbumRepository simplifiedAlbumRepository(R2dbcSimplifiedAlbumRepositoryDelegate delegate) {
            return new R2dbcSimplifiedAlbumRepository(delegate);
        }

    }
}
