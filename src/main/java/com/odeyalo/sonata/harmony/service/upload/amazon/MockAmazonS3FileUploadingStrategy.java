package com.odeyalo.sonata.harmony.service.upload.amazon;

import com.odeyalo.sonata.harmony.service.upload.FileUploadTarget;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import static com.odeyalo.sonata.harmony.service.upload.amazon.AmazonS3FileUploadingStrategy.UploadResult.of;

public class MockAmazonS3FileUploadingStrategy implements AmazonS3FileUploadingStrategy {
    private final PutObjectResponse response;
    private final String s3FileKey;

    public MockAmazonS3FileUploadingStrategy(PutObjectResponse response, String s3FileKey) {
        this.response = response;
        this.s3FileKey = s3FileKey;
    }

    @Override
    public Mono<UploadResult> uploadFile(FileUploadTarget uploadTarget) {
        UploadResult result = of(uploadTarget.getId(), s3FileKey, response);
        return Mono.just(result);
    }
}
