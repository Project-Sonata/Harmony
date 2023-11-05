package com.odeyalo.sonata.harmony.config.aws.support.resolver;

import com.odeyalo.sonata.harmony.config.aws.support.BucketNameSupplier;
import com.odeyalo.sonata.harmony.config.aws.support.MusicBucketNameSupplier;
import com.odeyalo.sonata.harmony.support.utils.FilenameUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class MusicFilePartBucketNameResolverSupport implements FilePartBucketNameResolverSupport {
    private final MusicBucketNameSupplier musicBucketNameSupplier;

    public MusicFilePartBucketNameResolverSupport(MusicBucketNameSupplier musicBucketNameSupplier) {
        this.musicBucketNameSupplier = musicBucketNameSupplier;
    }

    @Override
    @NotNull
    public Mono<BucketNameSupplier> resolveBucketName(@NotNull FilePart target) {
        if ( FilenameUtils.isMusicFile(target.filename()) ) {
            return Mono.just(musicBucketNameSupplier);
        }
        return Mono.empty();
    }
}