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
                .build();
    }
}
