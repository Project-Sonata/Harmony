package com.odeyalo.sonata.harmony.repository;

import com.odeyalo.sonata.harmony.entity.AlbumCoverImageEntity;
import reactor.core.publisher.Flux;

public interface AlbumCoverImageRepository extends BasicRepository<AlbumCoverImageEntity, Long> {

    Flux<AlbumCoverImageEntity> findAllByAlbumId(Long albumId);

}
