package com.odeyalo.sonata.harmony.repository.r2dbc;

import com.odeyalo.sonata.harmony.entity.TrackEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface R2dbcTrackRepository extends R2dbcRepository<TrackEntity, Long> {
}
