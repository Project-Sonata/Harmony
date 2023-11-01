package com.odeyalo.sonata.harmony.repository;

import com.odeyalo.sonata.harmony.entity.AlbumCoverImageEntity;
import reactor.core.publisher.Mono;

public interface AlbumCoverImageRepository extends BasicRepository<AlbumCoverImageEntity, Long> {

    Mono<AlbumCoverImageEntity> findByAlbumId(Long albumId);

}
