package com.odeyalo.sonata.harmony.service.album.support;

import com.odeyalo.sonata.harmony.model.Image;
import com.odeyalo.sonata.harmony.service.upload.FileUploadTarget;
import com.odeyalo.sonata.harmony.service.upload.FileUploader;
import com.odeyalo.sonata.harmony.service.upload.FileUploadingStatus;
import com.odeyalo.sonata.harmony.service.upload.FileUploadingStatus.UploadingFinishedEvent;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Collections;

import static com.odeyalo.sonata.harmony.service.upload.FileUploadingStatus.Type.UPLOADED;

@Component
public class DelegatingAlbumCoverImageUploader implements AlbumCoverImageUploader {
    private final FileUploader fileUploader;

    public DelegatingAlbumCoverImageUploader(FileUploader fileUploader) {
        this.fileUploader = fileUploader;
    }

    @Override
    public Mono<Image> uploadAlbumCoverImage(Mono<FileUploadTarget> target) {
        return target.flatMapMany(file -> fileUploader.uploadFile(Collections.singletonList(file)))
                .skipUntil(status -> status.getType() == UPLOADED)
                .next()
                .map(status -> mapToImage(status));
    }

    private static Image mapToImage(FileUploadingStatus status) {
        UploadingFinishedEvent finishedEvent = ((UploadingFinishedEvent) status.getEvent());
        return Image.urlOnly(finishedEvent.getUrl().getUrl());
    }
}
