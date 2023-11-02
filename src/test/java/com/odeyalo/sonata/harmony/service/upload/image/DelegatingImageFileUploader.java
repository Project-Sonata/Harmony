package com.odeyalo.sonata.harmony.service.upload.image;

import com.odeyalo.sonata.harmony.service.upload.FileUploadTarget;
import com.odeyalo.sonata.harmony.service.upload.FileUploader;
import com.odeyalo.sonata.harmony.service.upload.FileUploadingStatus;
import reactor.core.publisher.Flux;

import java.util.List;

public class DelegatingImageFileUploader implements ImageFileUploader {
    private final FileUploader delegate;

    public DelegatingImageFileUploader(FileUploader delegate) {
        this.delegate = delegate;
    }

    @Override
    public Flux<FileUploadingStatus> uploadFile(List<FileUploadTarget> files) {
        return delegate.uploadFile(files);
    }
}
