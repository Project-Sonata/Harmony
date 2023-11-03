package com.odeyalo.sonata.harmony.support.converter;

import com.odeyalo.sonata.harmony.entity.SimplifiedTrackEntity;
import com.odeyalo.sonata.harmony.entity.TrackContainerEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class TrackContainerEntityConverter {

    public TrackContainerEntity toTrackContainerEntity(List<SimplifiedTrackEntity> trackEntities) {
        return TrackContainerEntity.wrap(trackEntities);
    }
}
