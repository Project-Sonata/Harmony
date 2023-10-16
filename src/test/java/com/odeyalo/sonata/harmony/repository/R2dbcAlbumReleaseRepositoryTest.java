package com.odeyalo.sonata.harmony.repository;

import com.odeyalo.sonata.harmony.entity.AlbumReleaseEntity;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.test.context.ActiveProfiles;
import reactor.test.StepVerifier;

import static com.odeyalo.sonata.harmony.entity.AlbumReleaseEntity.builder;

@DataR2dbcTest
@TestInstance(Lifecycle.PER_CLASS)
@ActiveProfiles("test")
public class R2dbcAlbumReleaseRepositoryTest {

    @Autowired
    R2dbcAlbumReleaseRepository testable;

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
        testable.save(expected)
                .as(StepVerifier::create)
                .expectNextCount(1)
                .verifyComplete();
        // then
        testable.findById(expected.getId())
                .as(StepVerifier::create)
                .expectNextMatches(actual -> StringUtils.equals(expected.getAlbumName(), actual.getAlbumName()))
                .verifyComplete();
    }

    private static AlbumReleaseEntity createValidAlbumWithEmptyId() {
        return builder().albumName("dudeness").build();
    }
}