package com.odeyalo.sonata.harmony.support.converter;

import com.odeyalo.sonata.harmony.dto.TrackDto;
import com.odeyalo.sonata.harmony.model.Artist;
import com.odeyalo.sonata.harmony.service.album.TrackUploadTarget;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import testing.faker.TrackDtoFaker;
import testing.spring.MapStructBeansBootstrapConfiguration;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@Import(MapStructBeansBootstrapConfiguration.class)
class TrackUploadTargetConverterTest {

    @Autowired
    TrackUploadTargetConverter testable;

    @Test
    void shouldConvertToTrackUploadTarget() {
        var trackDto = TrackDtoFaker.create().get();

        TrackUploadTarget result = testable.toUploadTrackTarget(trackDto);

        assertThat(result).isNotNull();
        assertThat(result.getTrackName()).isEqualTo(trackDto.getTrackName());
        assertThat(result.getFileId()).isEqualTo(trackDto.getFileId());
        assertThat(result.getDurationMs()).isEqualTo(trackDto.getDurationMs());
        assertThat(result.getDiscNumber()).isEqualTo(trackDto.getDiscNumber());
        assertThat(result.getIndex()).isEqualTo(trackDto.getIndex());

        List<Artist> expectedArtists = getExpectedArtists(trackDto);

        assertThat(result.getArtists()).containsAll(expectedArtists);
    }

    @NotNull
    private static List<Artist> getExpectedArtists(TrackDto trackDto) {
        return trackDto.getArtists().getArtists()
                .stream()
                .map(dto -> Artist.builder().sonataId(dto.getId()).build())
                .toList();
    }
}