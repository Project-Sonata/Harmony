package com.odeyalo.sonata.harmony.support.converter;

import com.odeyalo.sonata.harmony.entity.ArtistContainerEntity;
import com.odeyalo.sonata.harmony.entity.ArtistEntity;
import com.odeyalo.sonata.harmony.entity.TrackEntity;
import com.odeyalo.sonata.harmony.model.Artist;
import com.odeyalo.sonata.harmony.model.ArtistContainer;
import com.odeyalo.sonata.harmony.model.Track;
import com.odeyalo.sonata.harmony.service.album.TrackUploadTarget;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import testing.spring.MapStructBeansBootstrapConfiguration;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@Import(MapStructBeansBootstrapConfiguration.class)
class TrackConverterTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    TrackConverter trackConverter;

    @Test
    void toTrack() {
        TrackUploadTarget expected = createTrackUploadTarget();

        Track result = trackConverter.toTrack(expected);

        assertThat(result.getTrackName()).isEqualTo(expected.getTrackName());
        assertThat(result.getIndex()).isEqualTo(expected.getIndex());
        assertThat(result.getDiscNumber()).isEqualTo(expected.getDiscNumber());
        assertThat(result.getArtists()).isEqualTo(expected.getArtists());
        assertThat(result.isExplicit()).isEqualTo(expected.isExplicit());
        assertThat(result.hasLyrics()).isEqualTo(expected.hasLyrics());
    }

    @Test
    void toTrackFromEntity() {
        TrackEntity expected = createTrackEntity();

        Track result = trackConverter.toTrack(expected);

        assertThat(result.getTrackName()).isEqualTo(expected.getName());
        assertThat(result.getIndex()).isEqualTo(expected.getIndex());
        assertThat(result.getDiscNumber()).isEqualTo(expected.getDiscNumber());
        assertThat(result.getDurationMs()).isEqualTo(expected.getDurationMs());
        assertThat(result.isExplicit()).isEqualTo(expected.isExplicit());
        assertThat(result.hasLyrics()).isEqualTo(expected.hasLyrics());
        assertThat(result.getTrackUrl()).isEqualTo(URI.create(expected.getTrackUrl()));
    }

    private static TrackEntity createTrackEntity() {
        return TrackEntity.builder()
                .name("dudeness")
                .index(1)
                .hasLyrics(true)
                .explicit(false)
                .artists(ArtistContainerEntity.solo(ArtistEntity.of(1L, "id", "corn wave")))
                .discNumber(1)
                .durationMs(1488L)
                .trackUrl("http://localhost:3000/track/123")
                .build();
    }

    private static TrackUploadTarget createTrackUploadTarget() {
        return TrackUploadTarget.builder()
                .trackName("dudeness")
                .index(1)
                .hasLyrics(true)
                .fileId("something")
                .isExplicit(false)
                .artists(ArtistContainer.solo(Artist.of("id", "corn wave")))
                .discNumber(1)
                .build();
    }
}