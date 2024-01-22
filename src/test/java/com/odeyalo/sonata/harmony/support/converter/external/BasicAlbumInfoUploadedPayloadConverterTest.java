package com.odeyalo.sonata.harmony.support.converter.external;

import com.odeyalo.sonata.harmony.model.AlbumRelease;
import com.odeyalo.sonata.suite.brokers.events.album.data.BasicAlbumInfoUploadedPayload;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import testing.faker.AlbumReleaseFaker;
import testing.spring.MapStructBeansBootstrapConfiguration;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@Import(MapStructBeansBootstrapConfiguration.class)
class BasicAlbumInfoUploadedPayloadConverterTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    BasicAlbumInfoUploadedPayloadConverter testable;

    @Test
    void shouldConvertName() {
        AlbumRelease expected = AlbumReleaseFaker.create().id(10L).get();

        BasicAlbumInfoUploadedPayload result = testable.toBasicAlbumInfoUploadedPayload(expected);

        assertThat(result.getAlbumName()).isEqualTo(expected.getName());
        assertThat(result.getTrackCount()).isEqualTo(expected.getTotalTracksCount());
    }
}