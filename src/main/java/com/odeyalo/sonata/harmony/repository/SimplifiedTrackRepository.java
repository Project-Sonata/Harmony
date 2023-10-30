package com.odeyalo.sonata.harmony.repository;

import com.odeyalo.sonata.harmony.entity.SimplifiedTrackEntity;
import com.odeyalo.sonata.harmony.entity.TrackEntity;
import reactor.core.publisher.Flux;

/**
 * Repository for SimplifiedTrackEntity
 */
public interface SimplifiedTrackRepository extends BasicRepository<SimplifiedTrackEntity, Long> {

    Flux<TrackEntity> findAllByAlbumId(Long id);

}
