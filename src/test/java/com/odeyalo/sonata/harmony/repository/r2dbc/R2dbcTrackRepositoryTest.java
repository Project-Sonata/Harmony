package com.odeyalo.sonata.harmony.repository.r2dbc;

import com.odeyalo.sonata.harmony.entity.TrackEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.test.context.ActiveProfiles;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.Objects;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

@DataR2dbcTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
public class R2dbcTrackRepositoryTest {
    @Autowired
    R2dbcTrackRepository testable;

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
                .durationMs(1000L)
                .explicit(true)
                .hasLyrics(true)
                .discNumber(1)
                .index(0)
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
                .build();
    }
}
