package com.odeyalo.sonata.harmony.config.aws.support.resolver;

import com.odeyalo.sonata.harmony.config.aws.support.BucketNameSupplier;
import com.odeyalo.sonata.harmony.config.aws.support.ImageBucketNameSupplier;
import com.odeyalo.sonata.harmony.support.utils.ImageUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class ImageFilePartBucketNameResolverSupport implements FilePartBucketNameResolverSupport {
    private final ImageBucketNameSupplier imageBucketSupplier;

    @Autowired
    public ImageFilePartBucketNameResolverSupport(ImageBucketNameSupplier imageBucketSupplier) {
        this.imageBucketSupplier = imageBucketSupplier;
    }

    @Override
    @NotNull
    public Mono<BucketNameSupplier> resolveBucketName(@NotNull FilePart target) {
        if ( ImageUtils.isImageFile(target.filename()) ) {
            return Mono.just(imageBucketSupplier);
        }
        return Mono.empty();
    }
}
