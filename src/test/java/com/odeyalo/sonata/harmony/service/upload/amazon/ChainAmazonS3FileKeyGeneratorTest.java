package com.odeyalo.sonata.harmony.service.upload.amazon;

import com.odeyalo.sonata.harmony.exception.AmazonS3FileKeyGenerationException;
import com.odeyalo.sonata.harmony.service.upload.FileUploadTarget;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import testing.spring.web.FilePartStub;

import static org.apache.commons.lang3.StringUtils.startsWith;

class ChainAmazonS3FileKeyGeneratorTest {
    private static final String GENERATED_FILE_KEY_IMAGE_PREFIX = "i/";
    private static final String GENERATED_FILE_KEY_MUSIC_PREFIX = "m/";

    @Test
    void shouldGenerateFileKeyWithImagePrefixIfImageFileIsUsed() {
        // given
        var testable = new ChainAmazonS3FileKeyGenerator<FileUploadTarget>();

        FileUploadTarget uploadTarget = createImageFileUploadTarget();
        // when-then
        testable.generateFileKey(uploadTarget)
                .as(StepVerifier::create)
                .expectNextMatches(actual -> startsWith(actual, GENERATED_FILE_KEY_IMAGE_PREFIX))
                .verifyComplete();
    }

    @Test
    void shouldGenerateFileKeyWithMusicPrefixIfMusicFileIsUsed() {
        // given
        var testable = new ChainAmazonS3FileKeyGenerator<FileUploadTarget>();

        FileUploadTarget uploadTarget = createTrackFileUploadTarget();
        // when-then
        testable.generateFileKey(uploadTarget)
                .as(StepVerifier::create)
                .expectNextMatches(actual -> startsWith(actual, GENERATED_FILE_KEY_MUSIC_PREFIX))
                .verifyComplete();
    }

    @Test
    void shouldReturnExceptionIfFileIsNotRecognizable() {
        // given
        var testable = new ChainAmazonS3FileKeyGenerator<FileUploadTarget>();

        FileUploadTarget uploadTarget = createUnrecognizableFileUploadTarget();
        // when-then
        testable.generateFileKey(uploadTarget)
                .as(StepVerifier::create)
                .expectError(AmazonS3FileKeyGenerationException.class)
                .verify();
    }

    @NotNull
    private FileUploadTarget createUnrecognizableFileUploadTarget() {
        return FileUploadTarget.of("ID", new FilePartStub(Flux.empty(), 1000L, "miku.pdf"));
    }

    @NotNull
    private static FileUploadTarget createImageFileUploadTarget() {
        return FileUploadTarget.of("ID", new FilePartStub(Flux.empty(), 1000L, "miku.png"));
    }

    @NotNull
    private static FileUploadTarget createTrackFileUploadTarget() {
        return FileUploadTarget.of("ID", new FilePartStub(Flux.empty(), 1000L, "miku.mp3"));
    }
}