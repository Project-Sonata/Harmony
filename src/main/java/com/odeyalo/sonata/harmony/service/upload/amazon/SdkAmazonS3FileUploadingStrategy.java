package com.odeyalo.sonata.harmony.service.upload.amazon;

import com.odeyalo.sonata.harmony.service.upload.FileUploadTarget;
import org.apache.commons.lang3.RandomStringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.core.async.AsyncRequestBody;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

import static com.odeyalo.sonata.harmony.service.upload.amazon.AmazonS3FileUploadingStrategy.UploadResult.of;
import static software.amazon.awssdk.core.async.AsyncRequestBody.fromPublisher;

/**
 * AmazonS3FileUploadingStrategy implementation that uses SDK provided by Amazon.
 */
@Component
public class SdkAmazonS3FileUploadingStrategy implements AmazonS3FileUploadingStrategy {
    private final S3AsyncClient s3Client;
    private final Supplier<String> bucketNameSupplier;

    @Autowired
    public SdkAmazonS3FileUploadingStrategy(S3AsyncClient s3Client,
                                            Supplier<String> bucketNameSupplier) {
        this.s3Client = s3Client;
        this.bucketNameSupplier = bucketNameSupplier;
    }

    @Override
    public Mono<UploadResult> uploadFile(FileUploadTarget uploadTarget) {
        String s3FileKey = RandomStringUtils.randomAlphanumeric(50);
        String bucketName = bucketNameSupplier.get();

        PutObjectRequest request = PutObjectRequest.builder()
                .key(s3FileKey)
                .bucket(bucketName)
                .contentLength(uploadTarget.getFilePart().headers().getContentLength())
                .build();

        AsyncRequestBody content = prepareContent(uploadTarget);

        CompletableFuture<PutObjectResponse> future = s3Client.putObject(request, content);

        return Mono.fromFuture(future)
                .map(response -> of(uploadTarget.getId(), s3FileKey, response));
    }

    @NotNull
    private static AsyncRequestBody prepareContent(FileUploadTarget uploadTarget) {
        return fromPublisher(uploadTarget.getFilePart().content().map(DataBuffer::asByteBuffer));
    }
}
