package com.odeyalo.sonata.harmony.service;

import com.odeyalo.sonata.harmony.model.AlbumType;
import com.odeyalo.sonata.harmony.service.album.UploadAlbumReleaseInfo;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import testing.spring.web.FilePartStub;

import java.util.Objects;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AlbumReleaseUploaderImplTest {

    AlbumReleaseUploaderImpl testable = new AlbumReleaseUploaderImpl();


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
                .expectNextMatches(actual ->  Objects.equals(albumReleaseInfo.getTotalTracksCount(), actual.getTotalTracksCount()))
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
        return UploadAlbumReleaseInfo.builder()
                .albumName("something")
                .totalTracksCount(1)
                .albumType(AlbumType.SINGLE)
                .build();
    }
}
