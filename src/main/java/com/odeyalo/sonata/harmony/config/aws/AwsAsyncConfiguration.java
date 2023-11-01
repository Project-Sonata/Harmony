package com.odeyalo.sonata.harmony.config.aws;

import com.odeyalo.sonata.harmony.service.upload.amazon.AmazonS3FileUrlResolver;
import com.odeyalo.sonata.harmony.service.upload.amazon.PrefixedUrlAmazonS3FileUrlResolver;
import com.odeyalo.sonata.harmony.support.properties.AwsProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.http.async.SdkAsyncHttpClient;
import software.amazon.awssdk.http.nio.netty.NettyNioAsyncHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.S3AsyncClientBuilder;
import software.amazon.awssdk.services.s3.S3Configuration;

import java.time.Duration;

@Configuration
public class AwsAsyncConfiguration {

    @Bean
    public AwsCredentialsProvider awsCredentials(AwsProperties awsProperties) {
        AwsProperties.Credentials credentials = awsProperties.getCredentials();

        return () -> AwsBasicCredentials.create(credentials.getKey(), credentials.getSecretKey());
    }

    @Bean
    public AmazonS3FileUrlResolver amazonS3FileUrlResolver(AwsProperties awsProperties) {
        return new PrefixedUrlAmazonS3FileUrlResolver(awsProperties.getUrlPrefix());
    }

    @Bean
    public S3AsyncClient s3AsyncClient(AwsCredentialsProvider credentialsProvider) {

        SdkAsyncHttpClient httpClient = NettyNioAsyncHttpClient.builder()
                .writeTimeout(Duration.ZERO)
                .maxConcurrency(64)
                .build();

        S3Configuration serviceConfiguration = S3Configuration.builder()
                .checksumValidationEnabled(false)
                .chunkedEncodingEnabled(true)
                .build();

        S3AsyncClientBuilder b = S3AsyncClient.builder().httpClient(httpClient)
                .region(Region.EU_NORTH_1)
                .credentialsProvider(credentialsProvider)
                .serviceConfiguration(serviceConfiguration);

        return b.build();
    }

}
