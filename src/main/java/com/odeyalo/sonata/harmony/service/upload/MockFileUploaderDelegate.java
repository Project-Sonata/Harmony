package com.odeyalo.sonata.harmony.service.upload;

import reactor.core.publisher.Mono;

/**
 * FileUploaderDelegate that returns predefined values
 */
public class MockFileUploaderDelegate implements FileUploaderDelegate {
    private final FileUrl result;

    public MockFileUploaderDelegate(FileUrl result) {
        this.result = result;
    }

    @Override
    public Mono<FileUrl> uploadFile(FileUploadTarget file) {
        return Mono.just(result);
    }
}
