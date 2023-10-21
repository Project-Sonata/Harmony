package com.odeyalo.sonata.harmony.repository;

import com.odeyalo.sonata.harmony.entity.ArtistEntity;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ArtistRepository extends BasicRepository<ArtistEntity, Long> {

    Mono<ArtistEntity> findBySonataId(String sonataId);

}
