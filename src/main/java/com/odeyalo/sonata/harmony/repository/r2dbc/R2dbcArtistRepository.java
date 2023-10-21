package com.odeyalo.sonata.harmony.repository.r2dbc;

import com.odeyalo.sonata.harmony.entity.ArtistEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface R2dbcArtistRepository extends R2dbcRepository<ArtistEntity, Long> {

    Mono<ArtistEntity> findBySonataId(String sonataId);
}
