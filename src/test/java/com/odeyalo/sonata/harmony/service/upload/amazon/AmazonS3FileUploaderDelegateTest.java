package com.odeyalo.sonata.harmony.service.upload.amazon;

import com.odeyalo.sonata.harmony.service.upload.FileUploadTarget;
import com.odeyalo.sonata.harmony.service.upload.FileUrl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.buffer.DefaultDataBuffer;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import testing.spring.web.FilePartStub;

import java.io.IOException;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AmazonS3FileUploaderDelegateTest {
    AmazonS3FileUploaderDelegate testable;

    static final String PREFIX = "https://cdn.sonata.com/";
    static final String S3_FILE_KEY = "autogeneratedid";

    @BeforeEach
    void setup() {
        var emptyResponse = PutObjectResponse.builder().build();

        var uploadingStrategy = new MockAmazonS3FileUploadingStrategy(emptyResponse, S3_FILE_KEY);

        AmazonS3FileUrlResolver amazonS3FileUrlResolver = new PrefixedUrlAmazonS3FileUrlResolver(PREFIX);

        testable = new AmazonS3FileUploaderDelegate(uploadingStrategy, amazonS3FileUrlResolver);
    }

    @Test
    void shouldUploadFileAndReturnUrl() throws IOException {
        var uploadTarget = getFileUploadTarget();

        testable.uploadFile(uploadTarget)
                .as(StepVerifier::create)
                .expectNext(FileUrl.urlOnly("https://cdn.sonata.com/autogeneratedid"))
                .verifyComplete();
    }


    private static FileUploadTarget getFileUploadTarget() throws IOException {
        var image = new ClassPathResource("./img/test-image.png");

        DefaultDataBufferFactory bufferFactory = new DefaultDataBufferFactory();

        DefaultDataBuffer dataBuffer = bufferFactory.wrap(image.getContentAsByteArray());

        FilePartStub file = new FilePartStub(Flux.just(dataBuffer), image.contentLength());

        return FileUploadTarget.builder()
                .id("uniqueid")
                .filePart(file)
                .build();
    }
}