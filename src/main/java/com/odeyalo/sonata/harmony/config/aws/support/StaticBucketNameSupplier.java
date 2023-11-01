package com.odeyalo.sonata.harmony.config.aws.support;

/**
 * BucketNameSupplier impl that returns static values only
 */
public class StaticBucketNameSupplier implements BucketNameSupplier {
    private final String bucketName;

    public StaticBucketNameSupplier(String bucketName) {
        this.bucketName = bucketName;
    }

    @Override
    public String get() {
        return bucketName;
    }
}
