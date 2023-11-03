package com.odeyalo.sonata.harmony.service.album.support;

import com.odeyalo.sonata.harmony.model.Image;
import com.odeyalo.sonata.harmony.service.upload.FileUploadTarget;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class MockAlbumCoverImageUploader implements AlbumCoverImageUploader {
    private final Image image;

    public MockAlbumCoverImageUploader(Image image) {
        this.image = image;
    }

    public MockAlbumCoverImageUploader(String url) {
        this.image = Image.urlOnly(url);
    }

    @Override
    public Mono<Image> uploadAlbumCoverImage(Mono<FileUploadTarget> targets) {
        return Mono.just(image);
    }
}
