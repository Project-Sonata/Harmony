package com.odeyalo.sonata.harmony.config;

import com.odeyalo.sonata.harmony.repository.r2dbc.support.release.FormattedString2ReleaseDateConverter;
import com.odeyalo.sonata.harmony.support.converter.*;
import com.odeyalo.sonata.harmony.support.converter.external.*;

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
        return new TrackConverterImpl(artistContainerConverter());
    }

    public TrackContainerConverter trackContainerConverter() {
        TrackContainerConverterImpl trackContainerConverter = new TrackContainerConverterImpl();
        return trackContainerConverter.setTrackConverter(trackConverter());
    }

    public ImageContainerConverter imageContainerConverter() {
        ImageContainerConverterImpl imageContainerConverter = new ImageContainerConverterImpl();
        return imageContainerConverter.setImageConverter(imageConverter());
    }

    public ImageConverter imageConverter() {
        return new ImageConverterImpl();
    }

    public ArtistContainerConverter artistContainerConverter() {
        ArtistContainerConverter artistContainerConverter = new ArtistContainerConverterImpl();
        return artistContainerConverter.setArtistConverter(artistConverter());
    }

    public ArtistConverter artistConverter() {
        return new ArtistConverterImpl();
    }

    public AlbumReleaseConverter albumReleaseConverter() {
        return new AlbumReleaseConverterImpl(imageContainerConverter(), artistContainerConverter(), trackContainerConverter());
    }

    public UploadedAlbumInfoDtoConverter uploadedAlbumInfoDtoConverter() {
        UploadedAlbumInfoDtoConverterImpl uploadedAlbumInfoDtoConverter = new UploadedAlbumInfoDtoConverterImpl(
                artistContainerDtoConverter(), simplifiedTrackDtoContainerConverter(), coverImagesConverter()
        );
        uploadedAlbumInfoDtoConverter.setReleaseDateEncoder(new FormattedString2ReleaseDateConverter());
        return uploadedAlbumInfoDtoConverter;
    }

    public CoverImagesConverter coverImagesConverter() {
        CoverImagesConverterImpl imagesConverter = new CoverImagesConverterImpl();
        imagesConverter.setImageCoverConverter(coverImageConverter());
        return imagesConverter;
    }

    public CoverImageConverter coverImageConverter() {
        return new CoverImageConverterImpl();
    }

    public SimplifiedTrackDtoContainerConverter simplifiedTrackDtoContainerConverter() {
        var converter = new SimplifiedTrackDtoContainerConverterImpl();
        converter.setTrackDtoConverter(simplifiedTrackDtoConverter());
        return converter;
    }

    public SimplifiedTrackDtoConverter simplifiedTrackDtoConverter() {
        return new SimplifiedTrackDtoConverterImpl(artistContainerDtoConverter());
    }

    public ArtistContainerDtoConverter artistContainerDtoConverter() {
        ArtistContainerDtoConverterImpl artistContainerDtoConverter = new ArtistContainerDtoConverterImpl();
        artistContainerDtoConverter.setArtistDtoConverter(artistDtoConverter());
        return artistContainerDtoConverter;
    }

    private ArtistDtoConverter artistDtoConverter() {
        return new ArtistDtoConverterImpl();
    }
}
