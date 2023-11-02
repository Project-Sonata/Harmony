package com.odeyalo.sonata.harmony.service.album.support;

import com.odeyalo.sonata.harmony.model.Image;
import com.odeyalo.sonata.harmony.service.upload.FileUploadTarget;
import reactor.core.publisher.Mono;

public interface AlbumCoverImageUploader {

    Mono<Image> uploadAlbumCoverImage(Mono<FileUploadTarget> targets);

}
