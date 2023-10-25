package com.odeyalo.sonata.harmony.service.upload.amazon;

import com.odeyalo.sonata.harmony.service.upload.FileUrl;
import reactor.core.publisher.Mono;

/**
 * Interface to resolve the FileUrl in the bucket by given file key.
 * Note that this interface does not depend on any bucket and can use any of them or search one by one.
 */
public interface AmazonS3FileUrlResolver {
    /**
     * Resolve URL of the file with the given
     * @param fileKey - key of the file in the bucket
     * @return - resolved url or empty Mono if the file does not exist
     */
    Mono<FileUrl> resolveUrl(String fileKey);

}