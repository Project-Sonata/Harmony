package com.odeyalo.sonata.harmony.support.converter;

import com.odeyalo.sonata.harmony.entity.SimplifiedTrackEntity;
import com.odeyalo.sonata.harmony.model.SimplifiedAlbumRelease;
import com.odeyalo.sonata.harmony.model.Track;
import com.odeyalo.sonata.harmony.service.album.TrackUploadTarget;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ArtistContainerConverter.class, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface TrackConverter {

    @Mapping(source = "hasLyrics", target = "hasLyrics")
    Track toTrack(TrackUploadTarget target);

    @Mapping(source = "hasLyrics", target = "hasLyrics")
    @Mapping(source = "name", target = "trackName")
    @Mapping(expression = "java( java.net.URI.create(entity.getTrackUrl()))", target = "trackUrl")
    Track toTrack(SimplifiedTrackEntity entity);

    default Track toTrack(SimplifiedTrackEntity entity, SimplifiedAlbumRelease album) {
        Track track = toTrack(entity);
        return track.toBuilder().album(album).build();
    }
}
