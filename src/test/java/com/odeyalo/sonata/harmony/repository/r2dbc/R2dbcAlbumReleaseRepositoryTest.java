package com.odeyalo.sonata.harmony.repository.r2dbc;

import com.odeyalo.sonata.harmony.entity.AlbumReleaseEntity;
import com.odeyalo.sonata.harmony.entity.ArtistContainerEntity;
import com.odeyalo.sonata.harmony.entity.ArtistEntity;
import com.odeyalo.sonata.harmony.model.AlbumType;
import com.odeyalo.sonata.harmony.model.ReleaseDate;
import com.odeyalo.sonata.harmony.repository.ArtistRepository;
import com.odeyalo.sonata.harmony.repository.r2dbc.callback.read.AlbumArtistsEnhancerAfterConvertCallback;
import com.odeyalo.sonata.harmony.repository.r2dbc.callback.read.AlbumReleaseDateEnhancerAfterConvertCallback;
import com.odeyalo.sonata.harmony.repository.r2dbc.callback.write.AlbumReleaseArtistsAssociationAfterSaveCallback;
import com.odeyalo.sonata.harmony.repository.r2dbc.callback.write.ReleaseDateAssociationReleaseAlbumEntityBeforeSaveCallback;
import com.odeyalo.sonata.harmony.repository.r2dbc.delegate.R2dbcAlbumReleaseRepositoryDelegate;
import com.odeyalo.sonata.harmony.repository.r2dbc.support.release.FormattedString2ReleaseDateConverter;
import com.odeyalo.sonata.harmony.repository.r2dbc.support.release.ReleaseDateDecoder;
import com.odeyalo.sonata.harmony.repository.r2dbc.support.release.ReleaseDateEncoder;
import com.odeyalo.sonata.harmony.repository.r2dbc.support.release.ReleaseDateRowInfo;
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

import java.util.List;
import java.util.Objects;

import static com.odeyalo.sonata.harmony.entity.AlbumReleaseEntity.builder;
import static com.odeyalo.sonata.harmony.entity.ArtistContainerEntity.solo;

@DataR2dbcTest
@TestInstance(Lifecycle.PER_CLASS)
@ActiveProfiles("test")
public class R2dbcAlbumReleaseRepositoryTest {

    @Autowired
    R2dbcAlbumReleaseRepository testable;

    @Autowired
    ArtistRepository artistRepository;

    @Autowired
    R2dbcAlbumArtistsRepository albumArtistsRepository;

    @AfterEach
    void tearDown() {
        albumArtistsRepository.deleteAll()
                .then(testable.deleteAll()
                        .then(artistRepository.deleteAll()))
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

    private static AlbumReleaseEntity createValidAlbumWithEmptyId() {
        ArtistContainerEntity artists = solo(ArtistEntity.builder()
                .sonataId("something")
                .name("corn wave")
                .build());

        return builder().albumName("dudeness")
                .albumType(AlbumType.SINGLE)
                .durationMs(1000L)
                .totalTracksCount(2)
                .releaseDate(ReleaseDate.onlyYear(2023))
                .artists(artists)
                .build();
    }

    private void insertReleases(AlbumReleaseEntity... entities) {
        testable.saveAll(List.of(entities))
                .as(StepVerifier::create)
                .expectNextCount(entities.length)
                .verifyComplete();
    }

    @TestConfiguration
    @Import(value = {
            ReleaseDateAssociationReleaseAlbumEntityBeforeSaveCallback.class,
            AlbumReleaseDateEnhancerAfterConvertCallback.class,
            AlbumArtistsEnhancerAfterConvertCallback.class,
            AlbumReleaseArtistsAssociationAfterSaveCallback.class,
            R2dbcArtistRepository.class
    })
    public static class Configuration {

        @Bean
        public ReleaseDateEncoder<String> releaseDateEncoder() {
            return new FormattedString2ReleaseDateConverter();
        }

        @Bean
        public ReleaseDateDecoder<ReleaseDateRowInfo> releaseDateDecoder() {
            return new FormattedString2ReleaseDateConverter();
        }

        @Bean
        public R2dbcAlbumReleaseRepository r2dbcAlbumReleaseRepository(R2dbcAlbumReleaseRepositoryDelegate delegate) {
            return new R2dbcAlbumReleaseRepository(delegate);
        }
    }
}
