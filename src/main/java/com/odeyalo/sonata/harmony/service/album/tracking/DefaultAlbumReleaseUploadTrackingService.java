package com.odeyalo.sonata.harmony.service.album.tracking;

import com.odeyalo.sonata.harmony.entity.AlbumReleaseTrackingEntity;
import com.odeyalo.sonata.harmony.model.AlbumRelease;
import com.odeyalo.sonata.harmony.repository.AlbumReleaseRepository;
import com.odeyalo.sonata.harmony.repository.AlbumReleaseTrackingRepository;
import com.odeyalo.sonata.harmony.support.converter.AlbumReleaseConverter;
import org.apache.commons.lang3.RandomStringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class DefaultAlbumReleaseUploadTrackingService implements AlbumReleaseUploadTrackingService {
    private final AlbumReleaseTrackingRepository releaseTrackingRepository;
    private final AlbumReleaseRepository albumReleaseRepository;
    private final AlbumReleaseConverter albumReleaseConverter;

    @Autowired
    public DefaultAlbumReleaseUploadTrackingService(AlbumReleaseTrackingRepository releaseTrackingRepository,
                                                    AlbumReleaseRepository albumReleaseRepository,
                                                    AlbumReleaseConverter albumReleaseConverter) {
        this.releaseTrackingRepository = releaseTrackingRepository;
        this.albumReleaseRepository = albumReleaseRepository;
        this.albumReleaseConverter = albumReleaseConverter;
    }

    @Override
    @NotNull
    public Mono<TrackingInfo> startTracking(@NotNull AlbumRelease albumRelease,
                                            @NotNull TrackingStatus trackingStatus) {

        if ( albumRelease.getId() == null ) {
            return Mono.error(new IllegalStateException("The ID of the AlbumRelease should be not null!"));
        }

        String trackingId = RandomStringUtils.randomAlphanumeric(30);
        var trackingEntity = AlbumReleaseTrackingEntity.of(trackingId, albumRelease.getId());

        return releaseTrackingRepository.save(trackingEntity)
                .map(saved -> TrackingInfo.of(saved.getTrackingId()));
    }

    @Override
    @NotNull
    public Mono<AlbumRelease> findByTrackingId(@NotNull String trackingId) {
        return releaseTrackingRepository.findByTrackingId(trackingId)
                .flatMap(trackingEntity -> albumReleaseRepository.findById(trackingEntity.getAlbumId()))
                .map(albumReleaseConverter::toAlbumRelease);
    }
}
