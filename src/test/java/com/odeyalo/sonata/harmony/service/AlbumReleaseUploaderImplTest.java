package com.odeyalo.sonata.harmony.service;

import com.odeyalo.sonata.harmony.service.album.UploadAlbumReleaseInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import testing.spring.web.FilePartStub;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AlbumReleaseUploaderImplTest {

    AlbumReleaseUploaderImpl testable = new AlbumReleaseUploaderImpl();


    @Test
    void shouldReturnSavedAlbum() {
        var albumReleaseInfo = UploadAlbumReleaseInfo.builder().build();
        Flux<FilePart> tracks = Flux.just(new FilePartStub(Flux.empty()));
        Mono<FilePart> albumCover = Mono.just(new FilePartStub(Flux.empty()));

        testable.uploadAlbumRelease(albumReleaseInfo, tracks, albumCover)
                .as(StepVerifier::create)
                .expectNextCount(1)
                .verifyComplete();
    }
}
