package com.odeyalo.sonata.harmony.repository.r2dbc;

import com.odeyalo.sonata.harmony.entity.TrackArtistEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/**
 * Repository to work with TrackArtistEntity
 */
@Repository
public interface R2dbcTrackArtistRepository extends R2dbcRepository<TrackArtistEntity, Long> {

    Flux<TrackArtistEntity> findAllByArtistIdAndTrackId(Long artistId, Long trackId);

    Flux<TrackArtistEntity> findAllByArtistId(Long artistId);

    Flux<TrackArtistEntity> findAllByTrackId(Long trackId);
}
