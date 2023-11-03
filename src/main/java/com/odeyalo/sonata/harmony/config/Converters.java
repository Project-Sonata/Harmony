package com.odeyalo.sonata.harmony.config;

import com.odeyalo.sonata.harmony.support.converter.*;

public class Converters {

    public ArtistContainerEntityConverter artistContainerEntityConverter() {
        ArtistContainerEntityConverterImpl converter = new ArtistContainerEntityConverterImpl();
        converter.setArtistEntityConverter(artistEntityConverter());
        return converter;
    }

    public ArtistEntityConverter artistEntityConverter() {
        return new ArtistEntityConverterImpl();
    }

    public TrackConverter trackConverter() {
        return new TrackConverterImpl();
    }

    public ImageContainerConverter imageContainerConverter() {
        ImageContainerConverterImpl imageContainerConverter = new ImageContainerConverterImpl();
        return imageContainerConverter.setImageConverter(imageConverter());
    }

    private ImageConverter imageConverter() {
        return new ImageConverterImpl();
    }

    public ArtistContainerConverter artistContainerConverter() {
        ArtistContainerConverter artistContainerConverter = new ArtistContainerConverterImpl();
        return artistContainerConverter.setArtistConverter(artistConverter());
    }

    private ArtistConverter artistConverter() {
        return new ArtistConverterImpl();
    }
}
