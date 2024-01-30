package com.odeyalo.sonata.harmony.repository.r2dbc.delegate;

import com.odeyalo.sonata.harmony.entity.AlbumReleaseEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface R2dbcAlbumReleaseRepositoryDelegate extends R2dbcRepository<AlbumReleaseEntity, Long> {

    @Query("update album_releases set duration_ms= :durationMs where id= :id")
    Mono<AlbumReleaseEntity> updateDuration(Long id, Long durationMs);

}
