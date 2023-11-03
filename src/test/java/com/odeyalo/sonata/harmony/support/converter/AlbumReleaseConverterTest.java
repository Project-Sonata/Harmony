package com.odeyalo.sonata.harmony.support.converter;

import com.odeyalo.sonata.harmony.entity.*;
import com.odeyalo.sonata.harmony.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import testing.spring.MapStructBeansBootstrapConfiguration;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@Import(MapStructBeansBootstrapConfiguration.class)
class AlbumReleaseConverterTest {

    @Autowired
    AlbumReleaseConverter testable;

    @Test
    void shouldConvertToValidValues() {
        AlbumReleaseEntity album = createAlbumReleaseEntity();

        AlbumRelease result = testable.toAlbumRelease(album);

        assertThat(result.getName()).isEqualTo(album.getAlbumName());
        assertThat(result.getAlbumType()).isEqualTo(album.getAlbumType());
        assertThat(result.getId()).isEqualTo(album.getId());
        assertThat(result.getTotalTracksCount()).isEqualTo(album.getTotalTracksCount());
    }

    @Test
    void shouldConvertImages() {
        AlbumReleaseEntity album = createAlbumReleaseEntity();

        AlbumRelease result = testable.toAlbumRelease(album);

        assertThat(result.getImages()).hasSize(album.getImages().size());

        AlbumCoverImageEntity firstImageEntity = album.getImages().get(0);

        assertThat(result.getImages()).first().matches(entity -> Objects.equals(entity.getHeight(), firstImageEntity.getHeight()));
        assertThat(result.getImages()).first().matches(entity -> Objects.equals(entity.getWidth(), firstImageEntity.getWidth()));
        assertThat(result.getImages()).first().matches(entity -> Objects.equals(entity.getUrl(), firstImageEntity.getUrl()));
    }

    @Test
    void shouldConvertTracks() {
        AlbumReleaseEntity album = createAlbumReleaseEntity();

        AlbumRelease result = testable.toAlbumRelease(album);

        assertThat(result.getTracks()).hasSize(album.getTracks().size());

        SimplifiedTrackEntity firstTrack = album.getTracks().get(0);

        assertThat(result.getTracks()).first().matches(entity -> Objects.equals(Long.valueOf(entity.getId()), firstTrack.getId()));
        assertThat(result.getTracks()).first().matches(entity -> Objects.equals(entity.getTrackName(), firstTrack.getName()));
        assertThat(result.getTracks()).first().matches(entity -> Objects.equals(entity.getDiscNumber(), firstTrack.getDiscNumber()));
        assertThat(result.getTracks()).first().matches(entity -> Objects.equals(entity.getIndex(), firstTrack.getIndex()));
        assertThat(result.getTracks()).first().matches(entity -> Objects.equals(entity.getDurationMs(), firstTrack.getDurationMs()));
    }

    @Test
    void shouldConvertArtists() {
        AlbumReleaseEntity album = createAlbumReleaseEntity();

        AlbumRelease result = testable.toAlbumRelease(album);

        assertThat(result.getArtists()).hasSize(1);

        Artist firstArtist = result.getArtists().get(0);

        assertThat(result.getArtists()).first().matches(actual -> Objects.equals(actual.getName(), firstArtist.getName()));
        assertThat(result.getArtists()).first().matches(actual -> Objects.equals(actual.getSonataId(), firstArtist.getSonataId()));
    }

    private static AlbumReleaseEntity createAlbumReleaseEntity() {
        ArtistContainerEntity artists = ArtistContainerEntity.solo(ArtistEntity.of(1L, "sonata id", "corn wave"));
        return AlbumReleaseEntity.builder()
                .albumName("dudeness")
                .images(createImages())
                .totalTracksCount(1)
                .artists(artists)
                .tracks(createTracks(artists))
                .albumType(AlbumType.SINGLE)
                .releaseDate(ReleaseDate.onlyYear(2023))
                .build();
    }

    private static AlbumCoverImageContainerEntity createImages() {
        return AlbumCoverImageContainerEntity.single(AlbumCoverImageEntity.builder()
                .width(300)
                .height(300)
                .url("https://cdn.sonata.com/i/image")
                .albumId(1L)
                .build());
    }

    private static TrackContainerEntity createTracks(ArtistContainerEntity artists) {
        return TrackContainerEntity.single(
                SimplifiedTrackEntity.builder()
                        .artists(artists)
                        .id(30L)
                        .discNumber(1)
                        .index(0)
                        .explicit(true)
                        .hasLyrics(true)
                        .durationMs(1000L)
                        .trackUrl("https://cdn.sonata.com/m/music")
                        .albumId(1L)
                        .artists(artists)
                        .build()
        );
    }
}