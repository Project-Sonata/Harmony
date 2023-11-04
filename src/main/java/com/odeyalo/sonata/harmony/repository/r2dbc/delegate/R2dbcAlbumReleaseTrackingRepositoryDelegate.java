package com.odeyalo.sonata.harmony.repository.r2dbc.delegate;

import com.odeyalo.sonata.harmony.entity.AlbumReleaseTrackingEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface R2dbcAlbumReleaseTrackingRepositoryDelegate extends R2dbcRepository<AlbumReleaseTrackingEntity, String> {
}
