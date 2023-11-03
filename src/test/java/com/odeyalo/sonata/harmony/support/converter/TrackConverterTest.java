package com.odeyalo.sonata.harmony.support.converter;

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

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@Import(MapStructBeansBootstrapConfiguration.class)
class TrackConverterTest {

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
        assertThat(result.getDurationMs()).isEqualTo(expected.getDurationMs());
        assertThat(result.isExplicit()).isEqualTo(expected.isExplicit());
        assertThat(result.hasLyrics()).isEqualTo(expected.hasLyrics());
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