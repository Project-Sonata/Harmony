package com.odeyalo.sonata.harmony.config.aws.support.resolver;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.buffer.DefaultDataBuffer;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import testing.spring.web.FilePartStub;

import java.io.IOException;

class MusicFilePartBucketNameResolverSupportTest {
    MusicFilePartBucketNameResolverSupport testable = new MusicFilePartBucketNameResolverSupport();

    @Test
    void shouldReturnEmptyMonoForNonTrackFile() throws IOException {
        var file = prepareNonTrackFile();

        testable.resolveBucketName(file)
                .as(StepVerifier::create)
                .verifyComplete();
    }

    @NotNull
    private static FilePartStub prepareNonTrackFile() throws IOException {
        var content = new ClassPathResource("./img/album-cover.png");

        DefaultDataBufferFactory bufferFactory = new DefaultDataBufferFactory();

        DefaultDataBuffer dataBuffer = bufferFactory.wrap(content.getContentAsByteArray());

        return new FilePartStub(Flux.just(dataBuffer), content.contentLength(), "album-cover.png", "image");
    }
}