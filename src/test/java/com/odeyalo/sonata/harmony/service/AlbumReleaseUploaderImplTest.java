package com.odeyalo.sonata.harmony.service;

import com.odeyalo.sonata.harmony.model.*;
import com.odeyalo.sonata.harmony.repository.AlbumReleaseRepository;
import com.odeyalo.sonata.harmony.repository.memory.InMemoryAlbumReleaseRepository;
import com.odeyalo.sonata.harmony.service.album.TrackUploadTarget;
import com.odeyalo.sonata.harmony.service.album.TrackUploadTargetContainer;
import com.odeyalo.sonata.harmony.service.album.UploadAlbumReleaseInfo;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import testing.spring.web.FilePartStub;

import java.util.Objects;
import java.util.Optional;

import static java.util.Objects.requireNonNull;
import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AlbumReleaseUploaderImplTest {


    static final String SAVED_IMAGE_URL = "https://cdn.sonata.com/i/image";

    AlbumReleaseRepository albumRepository = new InMemoryAlbumReleaseRepository();

    AlbumReleaseUploaderImpl testable;

    @BeforeEach
    void setUp() {

        testable = new AlbumReleaseUploaderImpl(albumRepository);

    }

    @Test
    void shouldReturnSavedAlbum() {
        var albumReleaseInfo = createUploadAlbumReleaseInfo();
        Flux<FilePart> tracks = prepareTrackFiles();
        Mono<FilePart> albumCover = prepareAlbumCoverFile();

        testable.uploadAlbumRelease(albumReleaseInfo, tracks, albumCover)
                .as(StepVerifier::create)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    void shouldReturnIdForAlbumRelease() {
        var albumReleaseInfo = createUploadAlbumReleaseInfo();
        Flux<FilePart> tracks = prepareTrackFiles();
        Mono<FilePart> albumCover = prepareAlbumCoverFile();

        testable.uploadAlbumRelease(albumReleaseInfo, tracks, albumCover)
                .as(StepVerifier::create)
                .expectNextMatches(actual -> actual.getId() != null)
                .verifyComplete();
    }

    @Test
    void shouldReturnSameNameAsProvided() {
        var albumReleaseInfo = createUploadAlbumReleaseInfo();
        Flux<FilePart> tracks = prepareTrackFiles();
        Mono<FilePart> albumCover = prepareAlbumCoverFile();

        testable.uploadAlbumRelease(albumReleaseInfo, tracks, albumCover)
                .as(StepVerifier::create)
                .expectNextMatches(actual -> Objects.equals(albumReleaseInfo.getAlbumName(), actual.getName()))
                .verifyComplete();
    }

    @Test
    void shouldReturnTotalTracksCount() {
        var albumReleaseInfo = createUploadAlbumReleaseInfo();
        Flux<FilePart> tracks = prepareTrackFiles();
        Mono<FilePart> albumCover = prepareAlbumCoverFile();

        testable.uploadAlbumRelease(albumReleaseInfo, tracks, albumCover)
                .as(StepVerifier::create)
                .expectNextMatches(actual -> Objects.equals(albumReleaseInfo.getTotalTracksCount(), actual.getTotalTracksCount()))
                .verifyComplete();
    }

    @Test
    void shouldReturnAlbumType() {
        var albumReleaseInfo = createUploadAlbumReleaseInfo();
        Flux<FilePart> tracks = prepareTrackFiles();
        Mono<FilePart> albumCover = prepareAlbumCoverFile();

        testable.uploadAlbumRelease(albumReleaseInfo, tracks, albumCover)
                .as(StepVerifier::create)
                .expectNextMatches(actual -> Objects.equals(albumReleaseInfo.getAlbumType(), actual.getAlbumType()))
                .verifyComplete();
    }

    @Test
    void shouldReturnArtists() {
        var albumReleaseInfo = createUploadAlbumReleaseInfo();
        Flux<FilePart> tracks = prepareTrackFiles();
        Mono<FilePart> albumCover = prepareAlbumCoverFile();

        testable.uploadAlbumRelease(albumReleaseInfo, tracks, albumCover)
                .as(StepVerifier::create)
                .expectNextMatches(actual -> Objects.equals(albumReleaseInfo.getArtists(), actual.getArtists()))
                .verifyComplete();
    }

    @Test
    void shouldReturnUploadedTracks() {
        var albumReleaseInfo = createUploadAlbumReleaseInfo();
        Flux<FilePart> trackFiles = prepareTrackFiles();
        Mono<FilePart> albumCover = prepareAlbumCoverFile();

        TrackContainer tracks = requireNonNull(testable.uploadAlbumRelease(albumReleaseInfo, trackFiles, albumCover).block()).getTracks();

        assertThat(tracks).hasSize(albumReleaseInfo.getTotalTracksCount());

        albumReleaseInfo.getTracks().forEach((track) -> assertTrack(tracks, track));
    }

    @Test
    void shouldReturnImagesForAlbumCover() {
        var albumReleaseInfo = createUploadAlbumReleaseInfo();
        Flux<FilePart> trackFiles = prepareTrackFiles();
        Mono<FilePart> albumCover = prepareAlbumCoverFile();

        testable.uploadAlbumRelease(albumReleaseInfo, trackFiles, albumCover)
                .as(StepVerifier::create)
                .expectNextMatches(actual -> Objects.equals(actual.getImages().get(0).getUrl(), SAVED_IMAGE_URL))
                .verifyComplete();
    }

    @Test
    void shouldSaveAlbumRelease() {
        var albumReleaseInfo = createUploadAlbumReleaseInfo();
        Flux<FilePart> trackFiles = prepareTrackFiles();
        Mono<FilePart> albumCover = prepareAlbumCoverFile();

        AlbumRelease release = requireNonNull(testable.uploadAlbumRelease(albumReleaseInfo, trackFiles, albumCover).block());

        albumRepository.findById(release.getId());
    }

    private static void assertTrack(TrackContainer tracks, TrackUploadTarget track) {
        Optional<Track> foundTrackOptional = tracks.findByTrackName(track.getTrackName());

        assertThat(foundTrackOptional).isPresent().as("Track should be returned!");

        Track foundTrack = foundTrackOptional.get();

        assertThat(foundTrack.getIndex()).isEqualTo(track.getIndex());
        assertThat(foundTrack.getDiscNumber()).isEqualTo(track.getDiscNumber());
        assertThat(foundTrack.getDurationMs()).isEqualTo(track.getDurationMs());
        assertThat(foundTrack.getTrackName()).isEqualTo(track.getTrackName());
        assertThat(foundTrack.getArtists()).isEqualTo(track.getArtists());
    }

    @NotNull
    private static Mono<FilePart> prepareAlbumCoverFile() {
        return Mono.just(new FilePartStub(Flux.empty()));
    }

    @NotNull
    private static Flux<FilePart> prepareTrackFiles() {
        return Flux.just(new FilePartStub(Flux.empty()));
    }

    @NotNull
    private static UploadAlbumReleaseInfo createUploadAlbumReleaseInfo() {
        ArtistContainer artists = ArtistContainer.solo(Artist.of("booones", "BONES"));

        TrackUploadTargetContainer tracks = TrackUploadTargetContainer.builder()
                .item(TrackUploadTarget.builder()
                        .trackName("dudeness")
                        .durationMs(1000L)
                        .isExplicit(true)
                        .hasLyrics(true)
                        .fileId("fileid")
                        .artists(artists)
                        .discNumber(1)
                        .index(0)
                        .build())
                .build();

        return UploadAlbumReleaseInfo.builder()
                .albumName("something")
                .totalTracksCount(1)
                .albumType(AlbumType.SINGLE)
                .tracks(tracks)
                .artists(artists)
                .build();
    }
}
