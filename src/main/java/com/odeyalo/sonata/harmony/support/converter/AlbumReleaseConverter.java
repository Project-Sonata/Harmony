package com.odeyalo.sonata.harmony.support.converter;

import com.odeyalo.sonata.harmony.entity.AlbumReleaseEntity;
import com.odeyalo.sonata.harmony.model.AlbumRelease;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ImageContainerConverter.class,
        ArtistContainerConverter.class, TrackContainerConverter.class}, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface AlbumReleaseConverter {

    @Mapping(source = "albumName", target = "name")
    AlbumRelease toAlbumRelease(AlbumReleaseEntity entity);

}
