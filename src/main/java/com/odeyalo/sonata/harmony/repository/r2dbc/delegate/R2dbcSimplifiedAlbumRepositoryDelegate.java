package com.odeyalo.sonata.harmony.repository.r2dbc.delegate;

import com.odeyalo.sonata.harmony.entity.SimplifiedAlbumEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface R2dbcSimplifiedAlbumRepositoryDelegate extends R2dbcRepository<SimplifiedAlbumEntity, Long> {
}
