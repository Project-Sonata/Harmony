package com.odeyalo.sonata.harmony.support.converter;

import com.odeyalo.sonata.harmony.entity.AlbumCoverImageEntity;
import com.odeyalo.sonata.harmony.model.Image;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ImageConverter {

    Image toImage(AlbumCoverImageEntity image);

}
