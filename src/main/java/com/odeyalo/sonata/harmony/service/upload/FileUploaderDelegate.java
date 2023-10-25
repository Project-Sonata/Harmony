package com.odeyalo.sonata.harmony.service.upload;

import reactor.core.publisher.Mono;

/**
 * Support interface that used as delegate to upload the file.
 * Implementation can use any kind of storage(AWS, Google Cloud, so on) and should return the valid url
 * for uploaded file.
 */
public interface FileUploaderDelegate {

    Mono<FileUrl> uploadFile(FileUploadTarget file);
}
