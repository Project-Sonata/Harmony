package com.odeyalo.sonata.harmony.repository;

import com.odeyalo.sonata.harmony.entity.AlbumReleaseEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface R2dbcAlbumReleaseRepository extends R2dbcRepository<AlbumReleaseEntity, Long> {
}
