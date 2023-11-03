package com.odeyalo.sonata.harmony.support.converter;

import com.odeyalo.sonata.harmony.entity.TrackContainerEntity;
import com.odeyalo.sonata.harmony.model.Track;
import com.odeyalo.sonata.harmony.model.TrackContainer;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class TrackContainerConverter {

    @Autowired
    TrackConverter trackConverter;

    public TrackContainer toTrackContainer(TrackContainerEntity containerEntity) {
        List<Track> tracks = containerEntity.stream().map(entity -> trackConverter.toTrack(entity)).toList();

        return TrackContainer.fromCollection(tracks);
    }
}
