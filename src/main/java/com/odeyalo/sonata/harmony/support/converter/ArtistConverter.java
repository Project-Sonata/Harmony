package com.odeyalo.sonata.harmony.support.converter;

import com.odeyalo.sonata.harmony.dto.ReleaseArtistDto;
import com.odeyalo.sonata.harmony.entity.ArtistEntity;
import com.odeyalo.sonata.harmony.model.Artist;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ArtistConverter {

    Artist toArtist(ArtistEntity artist);

    @Mapping(target = "sonataId", source = "id")
    Artist toArtist(ReleaseArtistDto artist);

}
