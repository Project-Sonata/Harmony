package com.odeyalo.sonata.harmony.support.converter;

import com.odeyalo.sonata.harmony.dto.UploadAlbumReleaseRequest;
import com.odeyalo.sonata.harmony.model.ArtistContainer;
import com.odeyalo.sonata.harmony.service.album.UploadAlbumReleaseInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {
        ArtistContainerConverter.class,
        TrackUploadTargetContainerConverter.class
})
public interface UploadAlbumReleaseInfoConverter {

    @Mapping(target = "artists", source = "performers")
    UploadAlbumReleaseInfo toUploadAlbumReleaseInfo(UploadAlbumReleaseRequest request);

}
