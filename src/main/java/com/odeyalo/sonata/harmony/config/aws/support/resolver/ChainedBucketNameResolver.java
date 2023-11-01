package com.odeyalo.sonata.harmony.config.aws.support.resolver;

import com.odeyalo.sonata.harmony.config.aws.support.BucketNameSupplier;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

/**
 * BucketNameResolver that uses a list of {@link BucketNameResolverSupport} with type T(same as provided in generics).
 * <p>
 * The implementation is designed for easy adding a bucket names and resolving them.
 * Every BucketNameResolverSupport uses a type T to determine if resolver can resolve the bucket name.
 * <p>
 * First NON-EMPTY result of BucketNameResolverSupport will be returned.
 * If all BucketNameResolverSupport have been invoked and nothing was found, then
 * fallback BucketNameSupplier will be used.
 * <p>
 * Implementation does not return null values or empty Mono.
 *
 * @param <T> - type that used for bucket name resolving
 */
@Component
public class ChainedBucketNameResolver<T> implements BucketNameResolver<T> {
    private final List<BucketNameResolverSupport<T>> resolvers;
    private final BucketNameSupplier defaultBucketNameSupplier;

    @Autowired
    public ChainedBucketNameResolver(List<BucketNameResolverSupport<T>> resolvers,
                                     @Qualifier("defaultBucketNameSupplier") BucketNameSupplier defaultBucketNameSupplier) {
        this.resolvers = Objects.requireNonNull(resolvers, "Resolvers must be not null. Empty list can be used");
        this.defaultBucketNameSupplier = Objects.requireNonNull(defaultBucketNameSupplier, "Default BucketNameSupplier must be set!");
    }

    @Override
    @NotNull
    public Mono<BucketNameSupplier> resolveBucketName(@NotNull T target) {
        return Flux.fromIterable(resolvers)
                .flatMap(resolver -> resolver.resolveBucketName(target))
                .next()
                .defaultIfEmpty(defaultBucketNameSupplier);
    }
}
