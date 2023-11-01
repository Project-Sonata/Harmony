package com.odeyalo.sonata.harmony.repository.r2dbc.delegate;

import com.odeyalo.sonata.harmony.entity.AlbumCoverImageEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface R2dbcAlbumCoverImageRepositoryDelegate extends R2dbcRepository<AlbumCoverImageEntity, Long> {

    Mono<AlbumCoverImageEntity> findByAlbumId(String albumId);

}
