package com.odeyalo.sonata.harmony.service.upload;

import org.jetbrains.annotations.NotNull;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import reactor.core.scheduler.Schedulers;

import java.util.List;

import static com.odeyalo.sonata.harmony.service.upload.FileUploadingStatus.UploadingFinishedEvent;
import static com.odeyalo.sonata.harmony.service.upload.FileUploadingStatus.UploadingStartedEvent;
import static com.odeyalo.sonata.harmony.service.upload.FileUploadingStatus.received;
import static com.odeyalo.sonata.harmony.service.upload.FileUploadingStatus.started;
import static com.odeyalo.sonata.harmony.service.upload.FileUploadingStatus.uploaded;

/**
 * FileUploader impl that just upload the file using FileUploaderDelegate and just pushes events
 */
public class DelegatingFileUploader implements FileUploader {
    private final FileUploaderDelegate delegate;

    public DelegatingFileUploader(FileUploaderDelegate delegate) {
        this.delegate = delegate;
    }

    @Override
    public Flux<FileUploadingStatus> uploadFile(List<FileUploadTarget> files) {
        Sinks.Many<FileUploadingStatus> eventPublisher = Sinks.many().unicast().onBackpressureBuffer();

        Flux.fromIterable(files)
                .doOnNext(uploadTarget -> publishFileReceivedEvent(eventPublisher, uploadTarget))
                .flatMap(file -> uploadFileAndPublishEvent(file, eventPublisher))
                .doOnComplete(eventPublisher::tryEmitComplete)
                .subscribeOn(Schedulers.boundedElastic())
                .subscribe();

        return eventPublisher.asFlux();
    }

    @NotNull
    private Mono<FileUrl> uploadFileAndPublishEvent(@NotNull FileUploadTarget file,
                                                   @NotNull Sinks.Many<FileUploadingStatus> eventPublisher) {
        FileUploadingStatus uploadStartedEvent = started(UploadingStartedEvent.of(file.getId()));

        eventPublisher.tryEmitNext(uploadStartedEvent);

        return delegate.uploadFile(file)
                .doOnSuccess((fileUrl -> publishUploadedEvent(file, eventPublisher, fileUrl)));
    }

    private static void publishFileReceivedEvent(@NotNull Sinks.Many<FileUploadingStatus> eventPublisher,
                                                 @NotNull FileUploadTarget uploadTarget) {

        FileUploadingStatus fileReceivedEvent = received(FileUploadingStatus.FileReceivedEvent.of(uploadTarget.getId()));

        eventPublisher.tryEmitNext(fileReceivedEvent);
    }

    private static void publishUploadedEvent(@NotNull FileUploadTarget file,
                                             @NotNull Sinks.Many<FileUploadingStatus> eventPublisher,
                                             @NotNull  FileUrl fileUrl) {

        FileUploadingStatus fileUploadedEvent = uploaded(UploadingFinishedEvent.of(file.getId(), fileUrl));

        eventPublisher.tryEmitNext(fileUploadedEvent);
    }
}
