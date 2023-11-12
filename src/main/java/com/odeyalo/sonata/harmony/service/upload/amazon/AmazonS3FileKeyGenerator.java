package com.odeyalo.sonata.harmony.service.upload.amazon;

import com.odeyalo.sonata.harmony.exception.AmazonS3FileKeyGenerationException;
import org.jetbrains.annotations.NotNull;
import reactor.core.publisher.Mono;

/**
 * Generate the file key for amazon S3 storage
 *
 * @param <T> - type that used to generate ID for
 */
public interface AmazonS3FileKeyGenerator<T> {
    /**
     * Generate file key for S3 storage.
     * The generated name should follow Amazon S3 filename rules,
     * <a href="https://docs.aws.amazon.com/AmazonS3/latest/userguide/object-keys.html">Amazon S3 File key naming conventions</a>
     *
     * @param t - type to generate key for
     * @return - string representation of file key or empty mono
     * @throws AmazonS3FileKeyGenerationException - if generator can't generate suitable file key.
     */
    @NotNull
    Mono<String> generateFileKey(@NotNull T t);

}
