package com.odeyalo.sonata.harmony.support.converter.external;

import com.odeyalo.sonata.harmony.model.TrackContainer;
import com.odeyalo.sonata.suite.brokers.events.album.data.SimplifiedTrackDto;
import com.odeyalo.sonata.suite.brokers.events.album.data.SimplifiedTrackDtoContainer;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class SimplifiedTrackDtoContainerConverter {
    @Autowired
    SimplifiedTrackDtoConverter trackDtoConverter;

    public SimplifiedTrackDtoContainerConverter() {
    }

    public SimplifiedTrackDtoContainerConverter(SimplifiedTrackDtoConverter trackDtoConverter) {
        this.trackDtoConverter = trackDtoConverter;
    }

    public SimplifiedTrackDtoContainer toTrackContainerDto(TrackContainer container) {
        var dtos = container.stream().map(trackDtoConverter::toSimplifiedTrackDto).toList();

        return SimplifiedTrackDtoContainer.multiple(dtos);
    }

    public SimplifiedTrackDtoContainerConverter setTrackDtoConverter(SimplifiedTrackDtoConverter trackDtoConverter) {
        this.trackDtoConverter = trackDtoConverter;
        return this;
    }
}
