package com.odeyalo.sonata.harmony.config.aws.support.resolver;

import com.odeyalo.sonata.harmony.config.aws.support.BucketNameSupplier;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Mono;

public class MusicFilePartBucketNameResolverSupport implements FilePartBucketNameResolverSupport {

    @Override
    @NotNull
    public Mono<BucketNameSupplier> resolveBucketName(@NotNull FilePart target) {
        return Mono.empty();
    }
}
