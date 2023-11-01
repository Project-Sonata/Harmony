package com.odeyalo.sonata.harmony.config.aws.support.resolver;

import com.odeyalo.sonata.harmony.config.aws.support.MusicBucketNameSupplier;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import testing.spring.web.FilePartStub;

import java.io.IOException;

class MusicFilePartBucketNameResolverSupportTest {
    final MusicBucketNameSupplier MUSIC_BUCKET_NAME_SUPPLIER = () -> "I Love Miku. And I am autistic sorry";

    MusicFilePartBucketNameResolverSupport testable = new MusicFilePartBucketNameResolverSupport(MUSIC_BUCKET_NAME_SUPPLIER);

    @Test
    void shouldReturnBucketNameForTrackFile() throws IOException {
        var filePart = prepareMusicFilePart();

        testable.resolveBucketName(filePart)
                .as(StepVerifier::create)
                .expectNext(MUSIC_BUCKET_NAME_SUPPLIER)
                .verifyComplete();
    }

    @Test
    void shouldReturnEmptyMonoForNonTrackFile() throws IOException {
        var filePart = prepareNonTrackFilePart();

        testable.resolveBucketName(filePart)
                .as(StepVerifier::create)
                .verifyComplete();
    }

    @NotNull
    private static FilePart prepareMusicFilePart() throws IOException {
        var content = new ClassPathResource("./music/test.mp3");

        DataBuffer dataBuffer = wrapToDataBuffer(content);

        return new FilePartStub(Flux.just(dataBuffer), content.contentLength(), "test.mp3", "tracks");
    }

    @NotNull
    private static FilePart prepareNonTrackFilePart() throws IOException {
        var content = new ClassPathResource("./img/album-cover.png");

        DataBuffer dataBuffer = wrapToDataBuffer(content);

        return new FilePartStub(Flux.just(dataBuffer), content.contentLength(), "album-cover.png", "image");
    }

    @NotNull
    private static DataBuffer wrapToDataBuffer(ClassPathResource content) throws IOException {
        DefaultDataBufferFactory bufferFactory = new DefaultDataBufferFactory();

        return bufferFactory.wrap(content.getContentAsByteArray());
    }
}