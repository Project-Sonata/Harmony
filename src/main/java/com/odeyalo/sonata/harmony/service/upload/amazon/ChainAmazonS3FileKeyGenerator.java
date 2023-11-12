package com.odeyalo.sonata.harmony.service.upload.amazon;

import com.odeyalo.sonata.harmony.exception.AmazonS3FileKeyGenerationException;
import com.odeyalo.sonata.harmony.service.upload.FileUploadTarget;
import com.odeyalo.sonata.harmony.support.utils.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.jetbrains.annotations.NotNull;
import reactor.core.publisher.Mono;

public class ChainAmazonS3FileKeyGenerator<T> implements AmazonS3FileKeyGenerator<T> {

    @Override
    @NotNull
    public Mono<String> generateFileKey(@NotNull T t) {
        if ( t instanceof FileUploadTarget fileUploadTarget ) {
            String filename = fileUploadTarget.getFilePart().filename();

            if ( FilenameUtils.isImageFile(filename) ) {
                return Mono.just("i/" + RandomStringUtils.randomAlphabetic(50));
            }

            if ( FilenameUtils.isMusicFile(filename) ) {
                return Mono.just("m/" + RandomStringUtils.randomAlphanumeric(50));
            }
        }
        return Mono.error(AmazonS3FileKeyGenerationException.withCustomMessage(
                "Failed to generate the file key for the given object: %s.\n No suitable generator found", t
        ));
    }
}
