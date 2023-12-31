package com.odeyalo.sonata.harmony.config.aws.support.resolver;

import com.odeyalo.sonata.harmony.config.aws.support.*;
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
import java.util.List;

class ChainedBucketNameResolverTest {

    static ImageBucketNameSupplier IMAGE_BUCKET_NAME_SUPPLIER = new StaticImageBucketNameSupplier("image");
    static MusicBucketNameSupplier MUSIC_BUCKET_NAME_SUPPLIER = new StaticMusicBucketNameSupplier("music");
    static BucketNameSupplier DEFAULT_BUCKET_NAME_SUPPLIER = new StaticBucketNameSupplier("fallback-bucket");

    ChainedBucketNameResolver<FilePart> resolvers = new ChainedBucketNameResolver<>(
            List.of(
                    new ImageFilePartBucketNameResolverSupport(IMAGE_BUCKET_NAME_SUPPLIER),
                    new MusicFilePartBucketNameResolverSupport(MUSIC_BUCKET_NAME_SUPPLIER)
            ),
            DEFAULT_BUCKET_NAME_SUPPLIER
    );

    @Test
    void shouldReturnMusicBucketNameForMusicFile() throws IOException {
        var musicFilePart = prepareMusicFile();

        resolvers.resolveBucketName(musicFilePart)
                .as(StepVerifier::create)
                .expectNext(MUSIC_BUCKET_NAME_SUPPLIER)
                .verifyComplete();
    }

    @Test
    void shouldReturnImageBucketNameForImageFile() throws IOException {
        var musicFilePart = prepareImageFile();

        resolvers.resolveBucketName(musicFilePart)
                .as(StepVerifier::create)
                .expectNext(IMAGE_BUCKET_NAME_SUPPLIER)
                .verifyComplete();
    }

    @Test
    void shouldReturnDefaultBucketNameIfNothingIsFound() throws IOException {
        FilePart unknownFilePart = prepareUnknownFile();

        resolvers.resolveBucketName(unknownFilePart)
                .as(StepVerifier::create)
                .expectNext(DEFAULT_BUCKET_NAME_SUPPLIER)
                .verifyComplete();
    }

    @NotNull
    private static FilePart prepareUnknownFile() throws IOException {
        var content = new ClassPathResource("./txt/test.txt");

        var dataBuffer = wrapToDataBuffer(content);

        return new FilePartStub(Flux.just(dataBuffer), content.contentLength(), "test.txt", "text");
    }

    @NotNull
    private static FilePartStub prepareImageFile() throws IOException {
        var content = new ClassPathResource("./img/album-cover.png");

        var dataBuffer = wrapToDataBuffer(content);

        return new FilePartStub(Flux.just(dataBuffer), content.contentLength(), "album-cover.png", "image");
    }

    @NotNull
    private static FilePartStub prepareMusicFile() throws IOException {
        var content = new ClassPathResource("./music/test.mp3");

        var dataBuffer = wrapToDataBuffer(content);

        return new FilePartStub(Flux.just(dataBuffer), content.contentLength(), "test.mp3", "tracks");
    }

    @NotNull
    private static DataBuffer wrapToDataBuffer(ClassPathResource content) throws IOException {
        DefaultDataBufferFactory bufferFactory = new DefaultDataBufferFactory();

        return bufferFactory.wrap(content.getContentAsByteArray());
    }
}