package com.odeyalo.sonata.harmony.repository;

import com.odeyalo.sonata.harmony.entity.AlbumReleaseTrackingEntity;
import reactor.core.publisher.Mono;


public interface AlbumReleaseTrackingRepository extends BasicRepository<AlbumReleaseTrackingEntity, Long> {

    Mono<AlbumReleaseTrackingEntity> findByTrackingId(String trackingId);
}
