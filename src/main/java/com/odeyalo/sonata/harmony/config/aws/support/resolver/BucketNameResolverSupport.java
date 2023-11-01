package com.odeyalo.sonata.harmony.config.aws.support.resolver;

import com.odeyalo.sonata.harmony.config.aws.support.BucketNameSupplier;
import org.jetbrains.annotations.NotNull;
import reactor.core.publisher.Mono;

/**
 * Central interface that used as support for {@link ChainedBucketNameResolver} and resolve the bucket name by T
 * @param <T> - target to check
 */
public interface BucketNameResolverSupport<T> {

    @NotNull
    Mono<BucketNameSupplier> resolveBucketName(@NotNull T target);

}
