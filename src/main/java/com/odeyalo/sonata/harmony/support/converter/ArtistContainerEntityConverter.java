package com.odeyalo.sonata.harmony.support.converter;

import com.odeyalo.sonata.harmony.entity.ArtistContainerEntity;
import com.odeyalo.sonata.harmony.entity.ArtistEntity;
import com.odeyalo.sonata.harmony.model.ArtistContainer;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring", uses = ArtistEntityConverter.class)
public abstract class ArtistContainerEntityConverter {

    @Autowired
    ArtistEntityConverter artistEntityConverter;

    public ArtistContainerEntity toArtistContainerEntity(ArtistContainer container) {
        List<ArtistEntity> artistEntities = container.stream().map(artist -> artistEntityConverter.toArtistEntity(artist)).toList();
        return ArtistContainerEntity.multiple(artistEntities);
    }
}
