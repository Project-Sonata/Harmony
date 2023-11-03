package com.odeyalo.sonata.harmony.support.converter;

import com.odeyalo.sonata.harmony.entity.AlbumCoverImageContainerEntity;
import com.odeyalo.sonata.harmony.model.Image;
import com.odeyalo.sonata.harmony.model.ImageContainer;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring", uses = ImageConverter.class)
public abstract class ImageContainerConverter {

    @Autowired
    ImageConverter imageConverter;

    public ImageContainer toImageContainer(AlbumCoverImageContainerEntity imageContainer) {
        List<Image> images = imageContainer.stream().map(imageConverter::toImage).toList();
        return ImageContainer.of(images);
    }

}
