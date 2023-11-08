package com.odeyalo.sonata.harmony.support.converter.external;

import com.odeyalo.sonata.harmony.model.Artist;
import com.odeyalo.sonata.suite.brokers.events.album.data.ArtistDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ArtistDtoConverter {

    @Mapping(target = "id", source = "sonataId")
    ArtistDto toArtistDto(Artist artist);
}
