package com.odeyalo.sonata.harmony.support.converter.external;

import com.odeyalo.sonata.harmony.model.AlbumRelease;
import com.odeyalo.sonata.harmony.repository.r2dbc.support.release.ReleaseDateEncoder;
import com.odeyalo.sonata.suite.brokers.events.album.data.UploadedAlbumInfoDto;
import org.mapstruct.AfterMapping;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = {
        ArtistContainerDtoConverter.class,
        SimplifiedTrackDtoContainerConverter.class,
        CoverImagesConverter.class
}, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class UploadedAlbumInfoDtoConverter {

    @Autowired
    ReleaseDateEncoder<String> releaseDateEncoder;

    public abstract UploadedAlbumInfoDto toUploadedAlbumInfoDto(AlbumRelease release);

    @AfterMapping
    public void releaseDateEnhancer(@MappingTarget UploadedAlbumInfoDto.UploadedAlbumInfoDtoBuilder builder,
                                    AlbumRelease release) {
        builder.releaseDateAsString(releaseDateEncoder.encodeReleaseDate(release.getReleaseDate()))
                .releaseDatePrecision(release.getReleaseDate().getPrecision().name());
    }

    public void setReleaseDateEncoder(ReleaseDateEncoder<String> releaseDateEncoder) {
        this.releaseDateEncoder = releaseDateEncoder;
    }
}
