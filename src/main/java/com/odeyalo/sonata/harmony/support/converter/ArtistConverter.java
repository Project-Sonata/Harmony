package com.odeyalo.sonata.harmony.support.converter;

import com.odeyalo.sonata.harmony.dto.ReleaseArtistDto;
import com.odeyalo.sonata.harmony.entity.ArtistEntity;
import com.odeyalo.sonata.harmony.model.Artist;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ArtistConverter {

    @Mapping(target = "name", source = "name")
    Artist toArtist(ArtistEntity artist);

    @Mapping(target = "sonataId", source = "id")
    @Mapping(target = "name", source = "artistName")
    Artist toArtist(ReleaseArtistDto artist);

}
