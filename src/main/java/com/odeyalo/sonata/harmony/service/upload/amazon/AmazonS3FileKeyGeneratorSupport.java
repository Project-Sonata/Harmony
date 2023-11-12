package com.odeyalo.sonata.harmony.service.upload.amazon;

import org.jetbrains.annotations.NotNull;
import reactor.core.publisher.Mono;

/**
 * Support interface to generate file key for Amazon S3.
 * Interface is designed to handle only one specific type T at the time/
 * @param <T> - type of T to generate file key for.
 * @see ChainAmazonS3FileKeyGenerator
 */
public interface AmazonS3FileKeyGeneratorSupport<T> {

    /**
     * Generate file key for S3 storage.
     * The generated name should follow Amazon S3 filename rules,
     * <a href="https://docs.aws.amazon.com/AmazonS3/latest/userguide/object-keys.html">Amazon S3 File key naming conventions</a>
     *
     * @param t - type to generate key for
     * @return - string representation of file key or empty mono if this generator does not support type T
     */
    @NotNull
    Mono<String> generateFileKey(@NotNull T t);

}
