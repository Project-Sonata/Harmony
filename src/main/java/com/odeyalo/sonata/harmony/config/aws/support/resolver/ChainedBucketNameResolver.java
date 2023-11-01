package com.odeyalo.sonata.harmony.config.aws.support.resolver;

import com.odeyalo.sonata.harmony.config.aws.support.BucketNameSupplier;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class ChainedBucketNameResolver<T> implements BucketNameResolver<T> {
    private final List<BucketNameResolverSupport<T>> resolvers;

    @Autowired
    public ChainedBucketNameResolver(List<BucketNameResolverSupport<T>> resolvers) {
        this.resolvers = resolvers;
    }

    @Override
    @NotNull
    public Mono<BucketNameSupplier> resolveBucketName(@NotNull T target) {
        return Flux.fromIterable(resolvers)
                .flatMap(resolver -> resolver.resolveBucketName(target))
                .next();
    }
}
