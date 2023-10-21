package com.odeyalo.sonata.harmony.repository.r2dbc.delegate;

import com.odeyalo.sonata.harmony.entity.ArtistEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

/**
 * Delegate interface that implement R2dbcRepository and used as delegate by {@link com.odeyalo.sonata.harmony.repository.r2dbc.R2dbcArtistRepository}
 */
public interface R2dbcArtistRepositoryDelegate extends R2dbcRepository<ArtistEntity, Long> {

    Mono<ArtistEntity> findBySonataId(String sonataId);

}
