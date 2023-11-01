package com.odeyalo.sonata.harmony.config.aws.support;

import java.util.Objects;

/**
 * BucketNameSupplier impl that returns static values only
 */
public class StaticBucketNameSupplier implements BucketNameSupplier {
    private final String bucketName;

    public StaticBucketNameSupplier(String bucketName) {
        this.bucketName = Objects.requireNonNull(bucketName, "Bucket name must be not null!");
    }

    @Override
    public String get() {
        return bucketName;
    }
}
