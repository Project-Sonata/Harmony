package com.odeyalo.sonata.harmony.config;

import com.odeyalo.sonata.harmony.support.converter.ArtistContainerEntityConverter;
import com.odeyalo.sonata.harmony.support.converter.ArtistContainerEntityConverterImpl;
import com.odeyalo.sonata.harmony.support.converter.ArtistEntityConverter;
import com.odeyalo.sonata.harmony.support.converter.ArtistEntityConverterImpl;

public class Converters {

    public ArtistContainerEntityConverter artistContainerEntityConverter() {
        ArtistContainerEntityConverterImpl converter = new ArtistContainerEntityConverterImpl();
        converter.setArtistEntityConverter(artistEntityConverter());
        return converter;
    }

    public ArtistEntityConverter artistEntityConverter() {
        return new ArtistEntityConverterImpl();
    }

}
