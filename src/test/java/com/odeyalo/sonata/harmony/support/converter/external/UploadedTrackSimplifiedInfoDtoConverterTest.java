package com.odeyalo.sonata.harmony.support.converter.external;

import com.odeyalo.sonata.harmony.model.Track;
import com.odeyalo.sonata.suite.brokers.events.album.data.UploadedTrackSimplifiedInfoDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import testing.faker.TrackFaker;
import testing.spring.MapStructBeansBootstrapConfiguration;

import static org.assertj.core.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@Import(MapStructBeansBootstrapConfiguration.class)
class UploadedTrackSimplifiedInfoDtoConverterTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    UploadedTrackSimplifiedInfoDtoConverter testable;

    @Test
    void shouldConvertFromTrack() {
        Track expected = TrackFaker.create().id("hello").get();

        UploadedTrackSimplifiedInfoDto result = testable.toUploadedTrackSimplifiedInfoDto(expected);

        assertThat(result.getName()).isEqualTo(expected.getTrackName());
        assertThat(result.getId()).isEqualTo(expected.getId());
        assertThat(result.getUri()).isEqualTo(expected.getTrackUrl());
        assertThat(result.getArtists()).hasSize(expected.getArtists().size());
    }
}