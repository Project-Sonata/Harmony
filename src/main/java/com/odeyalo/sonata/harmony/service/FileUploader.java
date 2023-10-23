package com.odeyalo.sonata.harmony.service;

import com.odeyalo.sonata.harmony.service.upload.FileUploadTarget;
import com.odeyalo.sonata.harmony.service.upload.FileUploadingStatus;
import com.odeyalo.sonata.harmony.service.upload.FileUploadingStatus.FileReceivedEvent;
import com.odeyalo.sonata.harmony.service.upload.FileUploadingStatus.UploadingFinishedEvent;
import com.odeyalo.sonata.harmony.service.upload.FileUploadingStatus.UploadingStartedEvent;
import org.jetbrains.annotations.NotNull;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import reactor.core.scheduler.Schedulers;

import java.nio.file.Path;
import java.util.List;

import static com.odeyalo.sonata.harmony.service.upload.FileUploadingStatus.*;

public class FileUploader {
    String path = "C:\\Users\\thepr\\IdeaProjects\\Sonata\\cdn-files\\src\\test\\resources\\";

    public Flux<FileUploadingStatus> uploadFile(List<FileUploadTarget> files) {
        Sinks.Many<FileUploadingStatus> sink = Sinks.many().unicast().onBackpressureBuffer();

        Flux.fromIterable(files).doOnNext(file -> {
                    String fileId = file.getId();
                    sink.tryEmitNext(received(FileReceivedEvent.of(fileId)));
                    sink.tryEmitNext(started(UploadingStartedEvent.of(fileId)));
                })
                .flatMap(file -> uploadAndPushEvent(sink, file))
                .doOnComplete(sink::tryEmitComplete)
                .subscribeOn(Schedulers.boundedElastic())
                .subscribe();

        return sink.asFlux().log();
    }

    @NotNull
    private Mono<Void> uploadAndPushEvent(Sinks.Many<FileUploadingStatus> sink, FileUploadTarget file) {
        return file.getFilePart()
                .transferTo(Path.of(path + file.getFilePart().filename()))
                .doOnSuccess((unused) -> sink.tryEmitNext(uploaded(UploadingFinishedEvent.of(file.getId(), "there is url"))));
    }
}
