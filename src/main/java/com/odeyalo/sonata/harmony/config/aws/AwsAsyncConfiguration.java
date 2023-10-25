package com.odeyalo.sonata.harmony.config.aws;

import org.springframework.beans.factory.annotation.Value;
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


    @Bean
    public AwsCredentialsProvider awsCredentials(@Value("${aws.key}") String awsKey,
                                                 @Value("${aws.secret.key}") String secretKey) {

        return () -> AwsBasicCredentials.create(awsKey, secretKey);
    }
}
