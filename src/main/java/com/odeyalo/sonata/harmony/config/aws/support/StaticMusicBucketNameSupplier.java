package com.odeyalo.sonata.harmony.config.aws.support;

/**
 * Static implementation of MusicBucketNameSupplier that always returns static value
 */
public class StaticMusicBucketNameSupplier implements MusicBucketNameSupplier {
    private final String musicBucket;

    public StaticMusicBucketNameSupplier(String musicBucket) {
        this.musicBucket = musicBucket;
    }

    @Override
    public String get() {
        return musicBucket;
    }
}
