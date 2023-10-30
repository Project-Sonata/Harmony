package com.odeyalo.sonata.harmony.repository.r2dbc.delegate;

import com.odeyalo.sonata.harmony.entity.SimplifiedTrackEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

/**
 * Delegate interface for SimplifiedTrackEntity entity
 */
public interface R2dbcSimplifiedTrackRepositoryDelegate extends R2dbcRepository<SimplifiedTrackEntity, Long> {

    Flux<SimplifiedTrackEntity> findAllByAlbumId(Long id);

}
