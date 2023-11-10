package com.odeyalo.sonata.harmony.support.converter.external;

import com.odeyalo.sonata.harmony.model.AlbumRelease;
import com.odeyalo.sonata.suite.brokers.events.album.data.UploadedAlbumInfoDto;
import org.mapstruct.AfterMapping;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {
        ArtistContainerDtoConverter.class,
        SimplifiedTrackDtoContainerConverter.class
}, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UploadedAlbumInfoDtoConverter {

    UploadedAlbumInfoDto toUploadedAlbumInfoDto(AlbumRelease release);

    @AfterMapping
    default void releaseDateEnhancer(@MappingTarget UploadedAlbumInfoDto.UploadedAlbumInfoDtoBuilder builder,
                                     AlbumRelease release) {
        builder.releaseDateAsString("CHANGE ME")
                .releaseDatePrecision(release.getReleaseDate().getPrecision().name());
    }
}
