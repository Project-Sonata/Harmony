package com.odeyalo.sonata.harmony.support.converter.external;

import com.odeyalo.sonata.harmony.model.ArtistContainer;
import com.odeyalo.sonata.suite.brokers.events.album.data.ArtistContainerDto;
import com.odeyalo.sonata.suite.brokers.events.album.data.ArtistDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring", uses = {
        ArtistDtoConverter.class
}, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class ArtistContainerDtoConverter {
    @Autowired
    ArtistDtoConverter artistDtoConverter;

    public ArtistContainerDto toArtistContainerDto(ArtistContainer artists) {
        List<ArtistDto> dtos = artists.stream()
                .map(artistDtoConverter::toArtistDto)
                .toList();
        return ArtistContainerDto.of(dtos);
    }

    public void setArtistDtoConverter(ArtistDtoConverter artistDtoConverter) {
        this.artistDtoConverter = artistDtoConverter;
    }
}
