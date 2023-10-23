package com.odeyalo.sonata.harmony.service;

import com.odeyalo.sonata.harmony.service.upload.FileUploadTarget;
import com.odeyalo.sonata.harmony.service.upload.FileUploadingStatus;
import com.odeyalo.sonata.harmony.service.upload.FileUploadingStatus.FileReceivedEvent;
import com.odeyalo.sonata.harmony.service.upload.FileUploadingStatus.UploadingFinishedEvent;
import com.odeyalo.sonata.harmony.service.upload.FileUploadingStatus.UploadingStartedEvent;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.buffer.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.nio.file.Path;

import static com.odeyalo.sonata.harmony.service.upload.FileUploadingStatus.received;
import static com.odeyalo.sonata.harmony.service.upload.FileUploadingStatus.started;
import static java.util.Collections.singletonList;
import static reactor.core.publisher.Mono.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FileUploaderTest {
    FileUploader testable = new FileUploader();

    @Test
    void shouldReturnUploadedFileValidUrl() throws IOException {
        var image = new ClassPathResource("./img/test-image.png");

        DefaultDataBufferFactory bufferFactory = new DefaultDataBufferFactory();

        DefaultDataBuffer dataBuffer = bufferFactory.wrap(image.getContentAsByteArray());

        FilePartStub file = new FilePartStub(Flux.just(dataBuffer));

        var fileUploadTarget = FileUploadTarget.builder()
                .id("uniqueid")
                .filePart(file)
                .build();

        Flux<FileUploadingStatus> statuses = testable.uploadFile(singletonList(fileUploadTarget));

        statuses.as(StepVerifier::create)
                .expectNext(received(FileReceivedEvent.of("uniqueid")))
                .expectNext(started(UploadingStartedEvent.of("uniqueid")))
                .expectNextMatches(actual ->
                        actual.getType() == FileUploadingStatus.Type.UPLOADED
                                && ((UploadingFinishedEvent) actual.getEvent()).getUrl() != null)
                .verifyComplete();
    }

    public static class FilePartStub implements FilePart {
        private final Flux<DataBuffer> content;

        public FilePartStub(Flux<DataBuffer> content) {
            this.content = content;
        }

        @Override
        @NotNull
        public String filename() {
            return "miku.png";
        }

        @Override
        @NotNull
        public Mono<Void> transferTo(@NotNull Path dest) {
            return DataBufferUtils.write(content, dest);
        }

        @Override
        @NotNull
        public String name() {
            return "image";
        }

        @Override
        @NotNull
        public HttpHeaders headers() {
            return HttpHeaders.EMPTY;
        }

        @Override
        @NotNull
        public Flux<DataBuffer> content() {
            return content;
        }
    }
}
