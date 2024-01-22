package com.odeyalo.sonata.harmony.support.converter.external;

import com.odeyalo.sonata.harmony.model.AlbumRelease;
import com.odeyalo.sonata.harmony.model.ReleaseDate;
import com.odeyalo.sonata.harmony.repository.r2dbc.support.release.ReleaseDateEncoder;
import com.odeyalo.sonata.suite.brokers.events.album.data.BasicAlbumInfoUploadedPayload;
import com.odeyalo.sonata.suite.brokers.events.album.data.CoverImage;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URI;

@Mapper(componentModel = "spring", uses = {
        ArtistContainerDtoConverter.class,
        UploadedTrackSimplifiedInfoContainerDtoConverter.class
})
public abstract class BasicAlbumInfoUploadedPayloadConverter {

    @Autowired
    ReleaseDateEncoder<String> releaseDateEncoder;


    @Mapping(source = "name", target = "albumName")
    @Mapping(source = "tracks", target = "uploadedTracks")
    @Mapping(source = "totalTracksCount", target = "trackCount")
    public abstract BasicAlbumInfoUploadedPayload toBasicAlbumInfoUploadedPayload(AlbumRelease albumRelease);


    @AfterMapping
    public void releaseDateEnhancer(@MappingTarget BasicAlbumInfoUploadedPayload.BasicAlbumInfoUploadedPayloadBuilder builder,
                                    AlbumRelease release) {
        ReleaseDate releaseDate = release.getReleaseDate();

        builder
                .releaseDateAsString(releaseDateEncoder.encodeReleaseDate(releaseDate))
                .releaseDatePrecision(releaseDate.getPrecision().name());
    }

    @AfterMapping
    public void imageEnhancer(@MappingTarget BasicAlbumInfoUploadedPayload.BasicAlbumInfoUploadedPayloadBuilder builder,
                              AlbumRelease release) {
        String imageUrl = release.getImages().get(0).getUrl();

        builder.coverImage(
                CoverImage.builder().uri(URI.create(imageUrl)).build()
        );
    }
}
