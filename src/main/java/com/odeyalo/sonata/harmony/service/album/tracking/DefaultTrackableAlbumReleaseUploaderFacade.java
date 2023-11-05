package com.odeyalo.sonata.harmony.service.album.tracking;

import com.odeyalo.sonata.harmony.service.AlbumReleaseUploader;
import com.odeyalo.sonata.harmony.service.album.UploadAlbumReleaseInfo;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.odeyalo.sonata.harmony.service.album.tracking.TrackingStatus.VERIFICATION_REQUIRED;

/**
 * Default impl of TrackableAlbumReleaseUploaderFacade
 */
@Component
public class DefaultTrackableAlbumReleaseUploaderFacade implements TrackableAlbumReleaseUploaderFacade {
    private final AlbumReleaseUploader albumReleaseUploader;
    private final AlbumReleaseUploadTrackingService trackingService;

    @Autowired
    public DefaultTrackableAlbumReleaseUploaderFacade(AlbumReleaseUploader albumReleaseUploader,
                                                      AlbumReleaseUploadTrackingService trackingService) {
        this.albumReleaseUploader = albumReleaseUploader;
        this.trackingService = trackingService;
    }

    @Override
    @NotNull
    public Mono<TrackingInfo> uploadAlbumRelease(@NotNull UploadAlbumReleaseInfo info,
                                                 @NotNull Flux<FilePart> tracks,
                                                 @NotNull Mono<FilePart> coverImage) {

        return albumReleaseUploader.uploadAlbumRelease(info, tracks, coverImage)
                .flatMap(albumRelease -> trackingService.startTracking(albumRelease, VERIFICATION_REQUIRED));
    }
}
