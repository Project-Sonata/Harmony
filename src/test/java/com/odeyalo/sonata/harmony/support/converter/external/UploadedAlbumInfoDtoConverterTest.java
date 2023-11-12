package com.odeyalo.sonata.harmony.support.converter.external;

import com.odeyalo.sonata.harmony.model.ReleaseDate;
import com.odeyalo.sonata.harmony.repository.r2dbc.support.release.FormattedString2ReleaseDateConverter;
import com.odeyalo.sonata.harmony.repository.r2dbc.support.release.ReleaseDateDecoder;
import com.odeyalo.sonata.harmony.repository.r2dbc.support.release.ReleaseDateEncoder;
import com.odeyalo.sonata.harmony.support.converter.external.UploadedAlbumInfoDtoConverterTest.Config;
import com.odeyalo.sonata.suite.brokers.events.album.data.UploadedAlbumInfoDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import testing.faker.AlbumReleaseFaker;
import testing.spring.MapStructBeansBootstrapConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@Import({MapStructBeansBootstrapConfiguration.class, Config.class})
class UploadedAlbumInfoDtoConverterTest {

    @Autowired
    UploadedAlbumInfoDtoConverter testable;

    @Test
    void toUploadedAlbumInfoDto() {
        var albumRelease = AlbumReleaseFaker.create().get();

        UploadedAlbumInfoDto result = testable.toUploadedAlbumInfoDto(albumRelease);

        assertThat(result).isNotNull();

        assertThat(result.getName()).isEqualTo(albumRelease.getName());
        assertThat(result.getAlbumType().name()).isEqualTo(albumRelease.getAlbumType().name());

        assertThat(result.getArtists()).hasSize(albumRelease.getArtists().size());
        assertThat(result.getTracks()).hasSize(albumRelease.getTracks().size());
    }

    @Test
    void shouldContainReleaseDate() {
        ReleaseDate releaseDate = ReleaseDate.withDay(3, 8, 2002);
        var albumRelease = AlbumReleaseFaker.create().releaseDate(releaseDate).get();
        UploadedAlbumInfoDto result = testable.toUploadedAlbumInfoDto(albumRelease);

        assertThat(result).isNotNull();
        assertThat(result.getReleaseDatePrecision()).isEqualTo(ReleaseDate.Precision.DAY.name());
        assertThat(result.getReleaseDateAsString()).isEqualTo("2002-08-03");
    }

    @TestConfiguration
    static class Config {

        @Bean
        public ReleaseDateEncoder<String> releaseDateDecoder() {
            return new FormattedString2ReleaseDateConverter();
        }
    }
}