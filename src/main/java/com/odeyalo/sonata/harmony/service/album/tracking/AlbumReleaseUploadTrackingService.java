package com.odeyalo.sonata.harmony.service.album.tracking;

import com.odeyalo.sonata.harmony.model.AlbumRelease;
import org.jetbrains.annotations.NotNull;
import reactor.core.publisher.Mono;

public interface AlbumReleaseUploadTrackingService {

    @NotNull
    Mono<TrackingInfo> startTracking(@NotNull AlbumRelease albumRelease,
                                     @NotNull TrackingStatus trackingStatus);

    @NotNull
    Mono<AlbumRelease> findByTrackingId(@NotNull String trackingId);
}
