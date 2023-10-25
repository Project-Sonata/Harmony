package com.odeyalo.sonata.harmony.service;

import com.odeyalo.sonata.harmony.service.upload.FileUploadTarget;
import com.odeyalo.sonata.harmony.service.upload.FileUploadingStatus;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * Upload the given file to some kind of storage and publish events about affected file
 */
public interface FileUploader {
    /**
     * Upload the file to storage and push the events to the current flux.
     *
     * Every event contain the ID of the file that event has affect on.
     * Implementations can queue the given files or upload them as soon as possible.
     *
     * The main goal of this class is event-driven file uploading using Flux of the events.
     * @param files files to upload
     * @return - flux with the events
     */
    Flux<FileUploadingStatus> uploadFile(List<FileUploadTarget> files);

}
