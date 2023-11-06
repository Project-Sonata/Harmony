package com.odeyalo.sonata.harmony.service.album.support;

import com.odeyalo.sonata.harmony.service.upload.FileUploadTarget;
import com.odeyalo.sonata.harmony.service.upload.FileUploader;
import com.odeyalo.sonata.harmony.service.upload.FileUploadingStatus;
import com.odeyalo.sonata.harmony.service.upload.FileUploadingStatus.UploadingFinishedEvent;
import com.odeyalo.sonata.harmony.service.upload.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import static com.odeyalo.sonata.harmony.service.upload.FileUploadingStatus.Type.UPLOADED;

/**
 * Delegate file uploading to FileUploader
 */
@Component
public class DelegatingAlbumTracksUploader implements AlbumTracksUploader {
    private final FileUploader fileUploader;

    @Autowired
    public DelegatingAlbumTracksUploader(FileUploader fileUploader) {
        this.fileUploader = fileUploader;
    }

    @Override
    public Flux<UploadedFile> uploadTracks(Flux<FileUploadTarget> tracks) {
        return tracks.collectList()
                .flatMapMany(fileUploader::uploadFile)
                .skipUntil(status -> status.getType() == UPLOADED)
                .map(DelegatingAlbumTracksUploader::mapToUploadedFile);
    }

    private static UploadedFile mapToUploadedFile(FileUploadingStatus status) {
        UploadingFinishedEvent event = ((UploadingFinishedEvent) status.getEvent());
        return UploadedFile.of(event.getFileId(), event.getUrl());
    }
}
