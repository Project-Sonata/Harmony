package com.odeyalo.sonata.harmony.config.aws.support;

/**
 * Static implementation of ImageBucketNameSupplier that always returns static value
 */
public class StaticImageBucketNameSupplier implements ImageBucketNameSupplier {
    private final String imageBucketName;

    public StaticImageBucketNameSupplier(String imageBucketName) {
        this.imageBucketName = imageBucketName;
    }

    @Override
    public String get() {
        return imageBucketName;
    }
}
