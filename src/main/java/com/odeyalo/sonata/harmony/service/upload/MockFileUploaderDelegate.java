package com.odeyalo.sonata.harmony.service.upload;

import reactor.core.publisher.Mono;

public class MockFileUploaderDelegate implements FileUploaderDelegate {

    @Override
    public Mono<FileUrl> uploadFile(FileUploadTarget file) {
        return Mono.just(FileUrl.urlOnly("something"));
    }
}
