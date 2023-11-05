package com.odeyalo.sonata.harmony.support.converter;

import com.odeyalo.sonata.harmony.dto.ReleaseArtistContainerDto;
import com.odeyalo.sonata.harmony.entity.ArtistContainerEntity;
import com.odeyalo.sonata.harmony.entity.ArtistEntity;
import com.odeyalo.sonata.harmony.model.Artist;
import com.odeyalo.sonata.harmony.model.ArtistContainer;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class ArtistContainerConverter {

    @Autowired
    ArtistConverter artistConverter;

    public ArtistContainer toArtistContainer(ArtistContainerEntity container) {
        List<Artist> artists = container.stream().map(artistConverter::toArtist).toList();
        return ArtistContainer.fromCollection(artists);
    }

    public ArtistContainer toArtistContainer(ReleaseArtistContainerDto container) {
        List<Artist> artists = container.getArtists().stream().map(artistConverter::toArtist).toList();
        return ArtistContainer.fromCollection(artists);
    }

    public ArtistContainer toArtistContainer(Collection<ArtistEntity> artistsCollection) {
        List<Artist> artists = artistsCollection.stream().map(artistConverter::toArtist).toList();
        return ArtistContainer.fromCollection(artists);
    }

    public ArtistContainerConverter setArtistConverter(ArtistConverter artistConverter) {
        this.artistConverter = artistConverter;
        return this;
    }
}
