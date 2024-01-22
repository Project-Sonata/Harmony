package com.odeyalo.sonata.harmony.support.converter.external;

import com.odeyalo.sonata.harmony.model.Track;
import com.odeyalo.sonata.suite.brokers.events.album.data.UploadedTrackSimplifiedInfoDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ArtistContainerDtoConverter.class)
public interface UploadedTrackSimplifiedInfoDtoConverter {

    @Mapping(source = "trackName", target = "name")
    @Mapping(source = "trackUrl", target = "uri")
    UploadedTrackSimplifiedInfoDto toUploadedTrackSimplifiedInfoDto(Track track);

}
