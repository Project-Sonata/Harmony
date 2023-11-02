package com.odeyalo.sonata.harmony.service;

import com.odeyalo.sonata.harmony.service.album.AlbumReleaseUploadingStatus;
import com.odeyalo.sonata.harmony.service.album.UploadAlbumReleaseInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import testing.spring.web.FilePartStub;

import static com.odeyalo.sonata.harmony.service.album.AlbumReleaseUploadingStatus.Type.RECEIVED;
import static com.odeyalo.sonata.harmony.service.album.AlbumReleaseUploadingStatus.Type.VALIDATION;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AlbumReleaseUploaderImplTest {

    AlbumReleaseUploaderImpl testable = new AlbumReleaseUploaderImpl();


    @Test
    void shouldReturnEventsInTheOrder() {
        var info = UploadAlbumReleaseInfo.of();
        Flux<FilePart> tracks = Flux.just(new FilePartStub(Flux.empty()));
        Mono<FilePart> coverImage = Mono.just(new FilePartStub(Flux.empty()));

        // received
        // validation
        // image upload
        // track upload
        // album info upload
        // finished_with_verification_required
        // finished_verified

        testable.uploadAlbumRelease(info, tracks, coverImage)
                .as(StepVerifier::create)
                .expectNextMatches(status -> compareStatusType(status, RECEIVED))
                .expectNextMatches(status -> compareStatusType(status, VALIDATION))
                .verifyComplete();
    }

    private static boolean compareStatusType(AlbumReleaseUploadingStatus status, AlbumReleaseUploadingStatus.Type expected) {
        return status.getType() == expected;
    }
}
