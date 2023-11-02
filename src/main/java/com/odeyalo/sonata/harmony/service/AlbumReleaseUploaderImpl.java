package com.odeyalo.sonata.harmony.service;

import com.odeyalo.sonata.harmony.service.album.AlbumReleaseUploadingStatus;
import com.odeyalo.sonata.harmony.service.album.UploadAlbumReleaseInfo;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import static com.odeyalo.sonata.harmony.service.album.AlbumReleaseUploadingStatus.Type.*;

public class AlbumReleaseUploaderImpl implements AlbumReleaseUploader {

    @Override
    @NotNull
    public Flux<AlbumReleaseUploadingStatus> uploadAlbumRelease(@NotNull UploadAlbumReleaseInfo info,
                                                                @NotNull Flux<FilePart> tracks,
                                                                @NotNull Mono<FilePart> coverImage) {

        Sinks.Many<AlbumReleaseUploadingStatus> eventPublisher = Sinks.many().unicast().onBackpressureBuffer();

        eventPublisher.tryEmitNext(AlbumReleaseUploadingStatus.of(RECEIVED));
        eventPublisher.tryEmitNext(AlbumReleaseUploadingStatus.of(VALIDATION));
        eventPublisher.tryEmitNext(AlbumReleaseUploadingStatus.of(IMAGE_UPLOAD_STARTED));
        eventPublisher.tryEmitNext(AlbumReleaseUploadingStatus.of(IMAGE_UPLOAD_FINISHED));

        eventPublisher.tryEmitComplete();

        return eventPublisher.asFlux();
    }
}
