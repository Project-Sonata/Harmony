package com.odeyalo.sonata.harmony.service.upload.amazon;

import com.odeyalo.sonata.harmony.service.upload.FileUrl;
import com.odeyalo.sonata.harmony.support.utils.UrlUtils;
import org.springframework.util.Assert;
import reactor.core.publisher.Mono;

/**
 * AmazonS3FileUrlResolver that just append the key to the end of the provided url
 */
public class PrefixedUrlAmazonS3FileUrlResolver implements AmazonS3FileUrlResolver {
    private final String prefix;

    public PrefixedUrlAmazonS3FileUrlResolver(String prefix) {
        Assert.notNull(prefix, "Prefix must be not null");
        Assert.state(UrlUtils.isValid(prefix), "Prefix should be valid URL");
        if (!prefix.endsWith("/")) {
            prefix += "/";
        }
        this.prefix = prefix;
    }

    @Override
    public Mono<FileUrl> resolveUrl(String fileKey) {
        return Mono.just(FileUrl.urlOnly(prefix + fileKey));
    }
}
