package com.odeyalo.sonata.harmony.repository.r2dbc;

import com.odeyalo.sonata.harmony.entity.*;
import com.odeyalo.sonata.harmony.model.AlbumType;
import com.odeyalo.sonata.harmony.model.ReleaseDate;
import com.odeyalo.sonata.harmony.repository.RemoveCapable;
import com.odeyalo.sonata.harmony.repository.SimplifiedTrackRepository;
import com.odeyalo.sonata.harmony.repository.r2dbc.delegate.R2dbcSimplifiedTrackRepositoryDelegate;
import org.apache.commons.lang3.StringUtils;
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
import reactor.test.StepVerifier;
import testing.spring.R2dbcEntityCallbackConfiguration;

import java.util.List;
import java.util.Objects;

import static com.odeyalo.sonata.harmony.entity.AlbumReleaseEntity.builder;
import static com.odeyalo.sonata.harmony.entity.ArtistContainerEntity.solo;

@DataR2dbcTest
@TestInstance(Lifecycle.PER_CLASS)
@ActiveProfiles("test")
@Import(value = {
        R2dbcAlbumReleaseRepositoryTest.Configuration.class,
        R2dbcEntityCallbackConfiguration.class
})
public class R2dbcAlbumReleaseRepositoryTest {

    @Autowired
    R2dbcAlbumReleaseRepository testable;

    @Autowired
    RemoveCapable<ArtistEntity, Long> artistRemover;

    @Autowired
    R2dbcAlbumArtistsRepository albumArtistsRepository;

    @AfterEach
    void tearDown() {
        albumArtistsRepository.deleteAll()
                .then(testable.deleteAll())
                .then(artistRemover.deleteAll())
                .block();
    }

    @Test
    void shouldSaveEntityAndGenerateId() {
        var albumReleaseEntity = createValidAlbumWithEmptyId();

        testable.save(albumReleaseEntity)
                .as(StepVerifier::create)
                .expectNextMatches(saved -> saved.getId() != null)
                .verifyComplete();
    }

    @Test
    void shouldSaveAlbumName() {
        var expected = createValidAlbumWithEmptyId();
        // when
        insertReleases(expected);
        // then
        testable.findById(expected.getId())
                .as(StepVerifier::create)
                .expectNextMatches(actual -> StringUtils.equals(expected.getAlbumName(), actual.getAlbumName()))
                .verifyComplete();
    }

    @Test
    void shouldSaveAlbumType() {
        var expected = createValidAlbumWithEmptyId();

        insertReleases(expected);

        testable.findById(expected.getId())
                .as(StepVerifier::create)
                .expectNextMatches(actual -> Objects.equals(expected.getAlbumType(), actual.getAlbumType()))
                .verifyComplete();
    }

    @Test
    void shouldSaveDurationMs() {
        var expected = createValidAlbumWithEmptyId();

        insertReleases(expected);

        testable.findById(expected.getId())
                .as(StepVerifier::create)
                .expectNextMatches(actual -> Objects.equals(expected.getDurationMs(), actual.getDurationMs()))
                .verifyComplete();
    }

    @Test
    void shouldSaveTotalTracksCount() {
        var expected = createValidAlbumWithEmptyId();

        insertReleases(expected);

        testable.findById(expected.getId())
                .as(StepVerifier::create)
                .expectNextMatches(actual -> Objects.equals(expected.getTotalTracksCount(), actual.getTotalTracksCount()))
                .verifyComplete();
    }

    @Test
    void shouldReturnReleaseDate() {
        var expected = createValidAlbumWithEmptyId();

        insertReleases(expected);

        testable.findById(expected.getId())
                .as(StepVerifier::create)
                .expectNextMatches(actual -> Objects.equals(expected.getReleaseDate(), actual.getReleaseDate()))
                .verifyComplete();
    }

    @Test
    void shouldSaveArtistsForTheAlbum() {
        var expected = createValidAlbumWithEmptyId();

        insertReleases(expected);

        testable.findById(expected.getId())
                .as(StepVerifier::create)
                .expectNextMatches(actual -> actual.getArtists() != null)
                .verifyComplete();
    }

    @Test
    void shouldSaveTracksForTheAlbum() {
        var expected = createValidAlbumWithEmptyId();

        insertReleases(expected);

        testable.findById(expected.getId())
                .as(StepVerifier::create)
                .expectNextMatches(actual -> Objects.equals(expected.getTracks(), actual.getTracks()))
                .verifyComplete();
    }

    private static AlbumReleaseEntity createValidAlbumWithEmptyId() {
        ArtistContainerEntity artists = solo(ArtistEntity.builder()
                .sonataId("something")
                .name("corn wave")
                .build());

        SimplifiedTrackEntity track = SimplifiedTrackEntity.builder()
                .name("domestic wolves")
                .artists(artists)
                .hasLyrics(true)
                .explicit(false)
                .index(0)
                .discNumber(1)
                .durationMs(7829L)
                .build();

        TrackContainerEntity trackContainer = TrackContainerEntity.single(track);

        return builder().albumName("dudeness")
                .albumType(AlbumType.SINGLE)
                .durationMs(1000L)
                .totalTracksCount(2)
                .releaseDate(ReleaseDate.onlyYear(2023))
                .artists(artists)
                .tracks(trackContainer)
                .build();
    }

    private void insertReleases(AlbumReleaseEntity... entities) {
        testable.saveAll(List.of(entities))
                .as(StepVerifier::create)
                .expectNextCount(entities.length)
                .verifyComplete();
    }

    @TestConfiguration
    static class Configuration {

        @Bean
        public SimplifiedTrackRepository r2dbcTrackRepository(R2dbcSimplifiedTrackRepositoryDelegate delegate) {
            return new R2dbcSimplifiedTrackRepository(delegate);
        }

    }
}
