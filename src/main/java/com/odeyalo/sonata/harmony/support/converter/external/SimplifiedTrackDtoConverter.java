package com.odeyalo.sonata.harmony.support.converter.external;

import com.odeyalo.sonata.harmony.model.Track;
import com.odeyalo.sonata.suite.brokers.events.album.data.SimplifiedTrackDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = ArtistContainerDtoConverter.class)
public interface SimplifiedTrackDtoConverter {

    @Mapping(target = "name", source = "trackName")
    @Mapping(target = "artists", source = "artists")
    @Mapping(target = "streamingUri" ,source = "trackUrl")
    SimplifiedTrackDto toSimplifiedTrackDto(Track track);

}
