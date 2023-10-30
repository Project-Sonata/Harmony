package com.odeyalo.sonata.harmony.repository.r2dbc.delegate;

import com.odeyalo.sonata.harmony.entity.TrackEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/**
 * Delegate that used to execute queries using R2DBC.
 * By default, default Spring implementation is used.
 */
@Repository
public interface R2dbcTrackRepositoryDelegate extends R2dbcRepository<TrackEntity, Long> {

    Flux<TrackEntity> findAllByAlbumId(Long id);

}
