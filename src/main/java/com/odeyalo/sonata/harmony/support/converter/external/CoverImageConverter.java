package com.odeyalo.sonata.harmony.support.converter.external;

import com.odeyalo.sonata.harmony.model.Image;
import com.odeyalo.sonata.suite.brokers.events.album.data.CoverImage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CoverImageConverter {

    @Mapping(target = "uri", expression = "java( java.net.URI.create( image.getUrl() ) )")
    CoverImage toCoverImage(Image image);

}
