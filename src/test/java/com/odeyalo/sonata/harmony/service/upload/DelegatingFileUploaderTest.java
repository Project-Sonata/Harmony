package com.odeyalo.sonata.harmony.service.upload;

import com.odeyalo.sonata.harmony.service.upload.FileUploadingStatus.FileReceivedEvent;
import com.odeyalo.sonata.harmony.service.upload.FileUploadingStatus.UploadingFinishedEvent;
import com.odeyalo.sonata.harmony.service.upload.FileUploadingStatus.UploadingStartedEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.buffer.DefaultDataBuffer;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import testing.spring.web.FilePartStub;

import java.io.IOException;
import java.time.Duration;

import static com.odeyalo.sonata.harmony.service.upload.FileUploadingStatus.received;
import static com.odeyalo.sonata.harmony.service.upload.FileUploadingStatus.started;
import static java.util.Collections.singletonList;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DelegatingFileUploaderTest {
    DelegatingFileUploader testable = new DelegatingFileUploader(
            new MockFileUploaderDelegate(FileUrl.urlOnly("https://cdn.sonata.com/uniqueid"))
    );

    @Test
    void shouldReturnUploadedFileValidUrl() throws IOException {
        FileUploadTarget fileUploadTarget = getFileUploadTarget();

        Flux<FileUploadingStatus> statuses = testable.uploadFile(singletonList(fileUploadTarget));

        StepVerifier.setDefaultTimeout(Duration.ofSeconds(5));

        statuses.as(StepVerifier::create)
                .expectNext(received(FileReceivedEvent.of("uniqueid")))
                .expectNext(started(UploadingStartedEvent.of("uniqueid")))
                .expectNextMatches(DelegatingFileUploaderTest::isUploaded)
                .verifyComplete();
    }


    private static boolean isUploaded(FileUploadingStatus actual) {
        return actual.getType() == FileUploadingStatus.Type.UPLOADED
                && ((UploadingFinishedEvent) actual.getEvent()).getUrl() != null;
    }

    private static FileUploadTarget getFileUploadTarget() throws IOException {
        var image = new ClassPathResource("./img/test-image.png");

        DefaultDataBufferFactory bufferFactory = new DefaultDataBufferFactory();

        DefaultDataBuffer dataBuffer = bufferFactory.wrap(image.getContentAsByteArray());

        FilePartStub file = new FilePartStub(Flux.just(dataBuffer));

        return FileUploadTarget.builder()
                .id("uniqueid")
                .filePart(file)
                .build();
    }
}
