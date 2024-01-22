package com.odeyalo.sonata.harmony.support.converter.external;

import com.odeyalo.sonata.harmony.model.TrackContainer;
import com.odeyalo.sonata.suite.brokers.events.album.data.UploadedTrackSimplifiedInfoContainerDto;
import com.odeyalo.sonata.suite.brokers.events.album.data.UploadedTrackSimplifiedInfoDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring",
        uses = UploadedTrackSimplifiedInfoDtoConverter.class,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class UploadedTrackSimplifiedInfoContainerDtoConverter {
    @Autowired
    UploadedTrackSimplifiedInfoDtoConverter uploadedTrackSimplifiedInfoDtoConverter;

    public UploadedTrackSimplifiedInfoContainerDto toUploadedTrackSimplifiedInfoContainerDto(TrackContainer container) {
        List<UploadedTrackSimplifiedInfoDto> list = container.stream()
                .map(uploadedTrackSimplifiedInfoDtoConverter::toUploadedTrackSimplifiedInfoDto)
                .toList();

        return UploadedTrackSimplifiedInfoContainerDto.fromCollection(list);
    }
}
