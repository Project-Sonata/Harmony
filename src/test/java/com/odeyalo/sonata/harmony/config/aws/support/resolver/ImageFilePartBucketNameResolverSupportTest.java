package com.odeyalo.sonata.harmony.config.aws.support.resolver;

import com.odeyalo.sonata.harmony.config.aws.support.ImageBucketNameSupplier;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.buffer.DefaultDataBuffer;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import testing.spring.web.FilePartStub;

import java.io.IOException;

class ImageFilePartBucketNameResolverSupportTest {
    ImageBucketNameSupplier IMAGE_BUCKET_SUPPLIER = () -> "mocked";

    ImageFilePartBucketNameResolverSupport testable = new ImageFilePartBucketNameResolverSupport(IMAGE_BUCKET_SUPPLIER);

    @Test
    void shouldReturnBucketNameIfTheFileIsImage() throws IOException {
        FilePartStub file = prepareImageFile();

        testable.resolveBucketName(file)
                .as(StepVerifier::create)
                .expectNext(IMAGE_BUCKET_SUPPLIER)
                .verifyComplete();
    }

    @Test
    void shouldReturnEmptyMonoIfFileIsNotImage() throws IOException {
        FilePartStub filePart = prepareNotImageFile();

        testable.resolveBucketName(filePart)
                .as(StepVerifier::create)
                .verifyComplete();
    }

    @NotNull
    private static FilePartStub prepareImageFile() throws IOException {
        var content = new ClassPathResource("./img/album-cover.png");

        DefaultDataBufferFactory bufferFactory = new DefaultDataBufferFactory();

        DefaultDataBuffer dataBuffer = bufferFactory.wrap(content.getContentAsByteArray());

        return new FilePartStub(Flux.just(dataBuffer), content.contentLength(), "album-cover.png", "image");
    }

    @NotNull
    private static FilePartStub prepareNotImageFile() throws IOException {
        var content = new ClassPathResource("./music/test.mp3");

        DefaultDataBufferFactory bufferFactory = new DefaultDataBufferFactory();

        DefaultDataBuffer dataBuffer = bufferFactory.wrap(content.getContentAsByteArray());

        return new FilePartStub(Flux.just(dataBuffer), content.contentLength(), "test.mp3", "tracks");
    }
}