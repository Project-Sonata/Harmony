package com.odeyalo.sonata.harmony.service;

import com.odeyalo.sonata.harmony.service.album.UploadAlbumReleaseInfo;
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
        Flux<FilePart> tracks = Flux.just(new FilePartStub(Flux.empty()));
        Mono<FilePart> albumCover = Mono.just(new FilePartStub(Flux.empty()));

        testable.uploadAlbumRelease(albumReleaseInfo, tracks, albumCover)
                .as(StepVerifier::create)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    void shouldReturnSameNameAsProvided() {
        var albumReleaseInfo = createUploadAlbumReleaseInfo();
        Flux<FilePart> tracks = Flux.just(new FilePartStub(Flux.empty()));
        Mono<FilePart> albumCover = Mono.just(new FilePartStub(Flux.empty()));

        testable.uploadAlbumRelease(albumReleaseInfo, tracks, albumCover)
                .as(StepVerifier::create)
                .expectNextMatches(actual -> Objects.equals(albumReleaseInfo.getAlbumName(), actual.getName()))
                .verifyComplete();
    }

    private static UploadAlbumReleaseInfo createUploadAlbumReleaseInfo() {
        return UploadAlbumReleaseInfo.builder()
                .albumName("something")
                .build();
    }
}
