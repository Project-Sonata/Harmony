package com.odeyalo.sonata.harmony.support.converter;

import com.odeyalo.sonata.harmony.dto.TrackDto;
import com.odeyalo.sonata.harmony.service.album.TrackUploadTarget;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = ArtistContainerConverter.class)
public interface TrackUploadTargetConverter {

    TrackUploadTarget toUploadTrackTarget(TrackDto trackDto);
}
