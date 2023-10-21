package com.odeyalo.sonata.harmony.repository.r2dbc;

import com.odeyalo.sonata.harmony.entity.TrackEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.test.context.ActiveProfiles;
import reactor.test.StepVerifier;

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


    private static TrackEntity generateTrackWithoutId() {
        return TrackEntity.builder()
                .name("nothing")
                .build();
    }
}
