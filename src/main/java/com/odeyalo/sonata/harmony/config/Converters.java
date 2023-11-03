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
}
