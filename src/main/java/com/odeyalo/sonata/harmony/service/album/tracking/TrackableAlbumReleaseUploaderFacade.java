package com.odeyalo.sonata.harmony.service.album.tracking;

import com.odeyalo.sonata.harmony.service.album.UploadAlbumReleaseInfo;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Facade to upload the album release and return the tracking info about album uploading.
 */
public interface TrackableAlbumReleaseUploaderFacade {

    @NotNull
    Mono<TrackingInfo> uploadAlbumRelease(@NotNull UploadAlbumReleaseInfo info,
                                          @NotNull Flux<FilePart> tracks,
                                          @NotNull Mono<FilePart> coverImage);

}
