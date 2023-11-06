package com.odeyalo.sonata.harmony.support.converter;

import com.odeyalo.sonata.harmony.dto.TrackContainerDto;
import com.odeyalo.sonata.harmony.service.album.TrackUploadTarget;
import com.odeyalo.sonata.harmony.service.album.TrackUploadTargetContainer;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class TrackUploadTargetContainerConverter {
    @Autowired
    TrackUploadTargetConverter trackUploadTargetConverter;

    public TrackUploadTargetContainer toTrackUploadTargetContainer(TrackContainerDto containerDto) {
        Collection<TrackUploadTarget> uploadTargets = containerDto.stream().map(trackUploadTargetConverter::toUploadTrackTarget).toList();
        return TrackUploadTargetContainer.fromCollection(uploadTargets);
    }
}
