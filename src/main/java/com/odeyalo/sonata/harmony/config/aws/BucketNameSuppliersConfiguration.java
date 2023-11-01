package com.odeyalo.sonata.harmony.config.aws;

import com.odeyalo.sonata.harmony.config.aws.support.*;
import com.odeyalo.sonata.harmony.support.properties.AwsProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Create {@link BucketNameSupplier} beans
 */
@Configuration
public class BucketNameSuppliersConfiguration {

    @Bean
    public ImageBucketNameSupplier imageBucketNameSupplier(AwsProperties properties) {
        return new StaticImageBucketNameSupplier(properties.getBuckets().getImageBucketName());
    }

    @Bean
    public MusicBucketNameSupplier musicBucketNameSupplier(AwsProperties properties) {
        return new StaticMusicBucketNameSupplier(properties.getBuckets().getMusicBucketName());
    }
}
