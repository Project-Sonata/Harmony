package com.odeyalo.sonata.harmony.repository.r2dbc;

import com.odeyalo.sonata.harmony.entity.AlbumArtistEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface R2dbcAlbumArtistsRepository extends R2dbcRepository<AlbumArtistEntity, Long> {

    Flux<AlbumArtistEntity> findAllByAlbumId(Long albumId);

    Flux<AlbumArtistEntity> findAllByArtistId(Long artistId);
}
