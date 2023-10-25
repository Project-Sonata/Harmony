package com.odeyalo.sonata.harmony.service.upload;

import reactor.core.publisher.Mono;

public class AmazonFileUploaderDelegate implements FileUploaderDelegate {

    @Override
    public Mono<FileUrl> uploadFile(FileUploadTarget file) {
        return null;
    }
}
