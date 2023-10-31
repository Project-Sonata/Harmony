package com.odeyalo.sonata.harmony.config.aws.support.resolver;

import com.odeyalo.sonata.harmony.config.aws.support.BucketNameSupplier;
import reactor.core.publisher.Mono;

/**
 * Resolve the bucket name from the T and return it.
 * @param <T> - type to resolve the bucket name
 */
public interface BucketNameResolver<T> {
    /**
     * Resolve the bucket name from the target T
     * @param target - target to resolve bucket name, in most cases this is file.
     * @return - mono with supplier implementation or empty mono if T does not supported by this resolver
     */
    Mono<BucketNameSupplier> resolveBucketName(T target);

}
