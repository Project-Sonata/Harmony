package com.odeyalo.sonata.harmony.config.aws.support;

/**
 * Default implementation that just returns predefined element
 */
public class BasicBucketNameSupplier implements BucketNameSupplier {
    private final String bucketName;

    public BasicBucketNameSupplier(String bucketName) {
        this.bucketName = bucketName;
    }

    @Override
    public String get() {
        return bucketName;
    }
}
