package com.odeyalo.sonata.harmony.service;

import com.odeyalo.sonata.harmony.service.album.AlbumReleaseUploadingStatus;
import com.odeyalo.sonata.harmony.service.album.UploadAlbumReleaseInfo;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Interface is used to upload the album release
 */
public interface AlbumReleaseUploader {

    @NotNull
    Flux<AlbumReleaseUploadingStatus> uploadAlbumRelease(@NotNull UploadAlbumReleaseInfo info,
                                                         @NotNull Flux<FilePart> tracks,
                                                         @NotNull Mono<FilePart> coverImage);
}
