package com.odeyalo.sonata.harmony.support.converter.external;

import com.odeyalo.sonata.harmony.model.ImageContainer;
import com.odeyalo.sonata.suite.brokers.events.album.data.CoverImage;
import com.odeyalo.sonata.suite.brokers.events.album.data.CoverImages;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring", uses = CoverImageConverter.class, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class CoverImagesConverter {
    @Autowired
    CoverImageConverter imageCoverConverter;

    public CoverImages toCoverImages(ImageContainer container) {
        List<CoverImage> images = container.stream().map(imageCoverConverter::toCoverImage).toList();
        return CoverImages.of(images);
    }

    public void setImageCoverConverter(CoverImageConverter imageCoverConverter) {
        this.imageCoverConverter = imageCoverConverter;
    }
}
