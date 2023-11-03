package com.odeyalo.sonata.harmony.support.converter;

import com.odeyalo.sonata.harmony.model.Track;
import com.odeyalo.sonata.harmony.service.album.TrackUploadTarget;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TrackConverter {

    @Mapping(source = "hasLyrics", target = "hasLyrics")
    Track toTrack(TrackUploadTarget target);


}
