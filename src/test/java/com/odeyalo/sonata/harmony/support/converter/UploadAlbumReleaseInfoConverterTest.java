package com.odeyalo.sonata.harmony.support.converter;

import com.odeyalo.sonata.harmony.dto.UploadAlbumReleaseRequest;
import com.odeyalo.sonata.harmony.model.Artist;
import com.odeyalo.sonata.harmony.service.album.UploadAlbumReleaseInfo;
import org.assertj.core.api.Assertions;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import testing.asserts.ArtistContainerAssert;
import testing.faker.UploadAlbumReleaseRequestFaker;
import testing.spring.MapStructBeansBootstrapConfiguration;

import java.util.List;

import static testing.asserts.UploadAlbumReleaseInfoAssert.assertThat;

@ExtendWith(SpringExtension.class)
@Import(MapStructBeansBootstrapConfiguration.class)
class UploadAlbumReleaseInfoConverterTest {

    @Autowired
    UploadAlbumReleaseInfoConverter testable;

    @Autowired
    TrackUploadTargetContainerConverter uploadTargetContainerConverter;

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class FromUploadAlbumReleaseRequest {

        @Test
        void shouldParseAlbumNameAndAlbumType() {
            var request = UploadAlbumReleaseRequestFaker.create().get();

            UploadAlbumReleaseInfo result = testable.toUploadAlbumReleaseInfo(request);

            assertThat(result)
                    .isNotNull()
                    .hasAlbumName(request.getAlbumName())
                    .hasAlbumType(request.getAlbumType());
        }

        @Test
        void shouldConvertTotalTracksCount() {
            var request = UploadAlbumReleaseRequestFaker.create().get();

            UploadAlbumReleaseInfo result = testable.toUploadAlbumReleaseInfo(request);

            assertThat(result).hasTotalTracksCount(request.getTracks().getTotalTracksCount());
        }

        @Test
        void shouldParseAlbumPerformers() {
            var request = UploadAlbumReleaseRequestFaker.create().get();

            UploadAlbumReleaseInfo result = testable.toUploadAlbumReleaseInfo(request);

            List<Artist> expectedArtists = getExpectedArtists(request);

            ArtistContainerAssert.assertThat(result.getArtists())
                    .containsAll(expectedArtists);
        }

        @Test
        void shouldParseReleaseDate() {
            var request = UploadAlbumReleaseRequestFaker.create().get();

            UploadAlbumReleaseInfo result = testable.toUploadAlbumReleaseInfo(request);

            assertThat(result)
                    .hasReleaseDate(request.getReleaseDate());
        }

        @Test
        void shouldParseAlbumTracks() {
            var request = UploadAlbumReleaseRequestFaker.create().get();
            var expectedTracks = uploadTargetContainerConverter.toTrackUploadTargetContainer(request.getTracks());

            UploadAlbumReleaseInfo result = testable.toUploadAlbumReleaseInfo(request);

            Assertions.assertThat(result.getTracks()).containsAll(expectedTracks);
        }

        @NotNull
        private List<Artist> getExpectedArtists(UploadAlbumReleaseRequest request) {
            return request.getPerformers().getArtists()
                    .stream()
                    .map(dto -> Artist.builder().sonataId(dto.getId()).build())
                    .toList();
        }
    }
}