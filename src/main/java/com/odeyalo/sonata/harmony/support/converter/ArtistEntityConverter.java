package com.odeyalo.sonata.harmony.support.converter;

import com.odeyalo.sonata.harmony.entity.ArtistEntity;
import com.odeyalo.sonata.harmony.model.Artist;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ArtistEntityConverter {

    ArtistEntity toArtistEntity(Artist artist);

}
