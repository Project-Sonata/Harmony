package com.odeyalo.sonata.harmony.service.upload.amazon;

import com.odeyalo.sonata.harmony.exception.AmazonS3FileKeyGenerationException;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * Chain implementation of AmazonS3FileKeyGenerator that uses {@link AmazonS3FileKeyGeneratorSupport} to generate the file key.
 * Pick the first generated item and return it. If there is no item was generated, throw the AmazonS3FileKeyGenerationException
 * @param <T> - type to generate file key for
 */
@Component
public class ChainAmazonS3FileKeyGenerator<T> implements AmazonS3FileKeyGenerator<T> {
    private final List<AmazonS3FileKeyGeneratorSupport<T>> generators;

    @Autowired
    public ChainAmazonS3FileKeyGenerator(List<AmazonS3FileKeyGeneratorSupport<T>> generators) {
        Assert.notEmpty(generators, "List must contain at least one element!");
        this.generators = generators;
    }

    @Override
    @NotNull
    public Mono<String> generateFileKey(@NotNull T fileKeyGenerationTarget) {
        return Flux.fromIterable(generators)
                .flatMap(generator -> generator.generateFileKey(fileKeyGenerationTarget))
                .next()
                .switchIfEmpty(onEmptyFileKeyException(fileKeyGenerationTarget));
    }

    @NotNull
    private static <T> Mono<String> onEmptyFileKeyException(@NotNull T t) {
        return Mono.error(AmazonS3FileKeyGenerationException.withCustomMessage(
                "Failed to generate the file key for the given object: %s.\n No suitable generator found", t
        ));
    }
}
