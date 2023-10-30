package com.odeyalo.sonata.harmony.repository;

import com.odeyalo.sonata.harmony.entity.TrackEntity;
import reactor.core.publisher.Flux;

/**
 * Repository to work with {@link TrackEntity}
 */
public interface TrackRepository extends BasicRepository<TrackEntity, Long> {

    Flux<TrackEntity> findAllByAlbumId(Long id);
}