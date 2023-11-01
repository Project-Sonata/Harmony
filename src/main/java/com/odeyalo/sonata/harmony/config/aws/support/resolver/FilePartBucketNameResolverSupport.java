package com.odeyalo.sonata.harmony.config.aws.support.resolver;

import org.springframework.http.codec.multipart.FilePart;

/**
 * Resolve the bucket name from the {@link FilePart}
 */
public interface FilePartBucketNameResolverSupport extends BucketNameResolverSupport<FilePart> {
}
