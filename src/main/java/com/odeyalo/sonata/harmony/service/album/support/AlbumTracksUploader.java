package com.odeyalo.sonata.harmony.service.album.support;

import com.odeyalo.sonata.harmony.service.upload.FileUploadTarget;
import com.odeyalo.sonata.harmony.service.upload.UploadedFile;
import reactor.core.publisher.Flux;

public interface AlbumTracksUploader {

    Flux<UploadedFile> uploadTracks(Flux<FileUploadTarget> tracks);
}
