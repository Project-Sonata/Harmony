package com.odeyalo.sonata.harmony.service.upload.amazon;

import com.odeyalo.sonata.harmony.config.aws.support.BucketNameSupplier;
import com.odeyalo.sonata.harmony.config.aws.support.resolver.BucketNameResolver;
import com.odeyalo.sonata.harmony.service.upload.FileUploadTarget;
import org.apache.commons.lang3.RandomStringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.core.async.AsyncRequestBody;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import static com.odeyalo.sonata.harmony.service.upload.amazon.AmazonS3FileUploadingStrategy.UploadResult.of;
import static software.amazon.awssdk.core.async.AsyncRequestBody.fromPublisher;

/**
 * AmazonS3FileUploadingStrategy implementation that uses SDK provided by Amazon.
 */
@Component
public class SdkAmazonS3FileUploadingStrategy implements AmazonS3FileUploadingStrategy {
    private final S3AsyncClient s3Client;
    private final BucketNameResolver<FilePart> bucketNameResolver;

    @Autowired
    public SdkAmazonS3FileUploadingStrategy(S3AsyncClient s3Client,
                                            BucketNameResolver<FilePart> bucketNameResolver) {
        this.s3Client = s3Client;
        this.bucketNameResolver = bucketNameResolver;
    }

    @Override
    public Mono<UploadResult> uploadFile(FileUploadTarget uploadTarget) {
        String s3FileKey = RandomStringUtils.randomAlphanumeric(50);

        Mono<BucketNameSupplier> bucketNameSupplierMono = bucketNameResolver.resolveBucketName(uploadTarget.getFilePart());

        return bucketNameSupplierMono.flatMap(bucketNameSupplier -> {

            Mono<PutObjectResponse> s3RequestSender = prepareAndSendRequestToS3(uploadTarget, s3FileKey, bucketNameSupplier.get());

            return s3RequestSender
                    .map(response -> of(uploadTarget.getId(), s3FileKey, response))
                    .log();
        });
    }

    private Mono<PutObjectResponse> prepareAndSendRequestToS3(@NotNull FileUploadTarget uploadTarget,
                                                              @NotNull String s3FileKey,
                                                              @NotNull String bucketName) {

        Mono<PutObjectRequest> requestMono = preparePutObjectRequest(uploadTarget, s3FileKey, bucketName);

        AsyncRequestBody content = prepareContent(uploadTarget);

        return requestMono.flatMap(requestBody -> Mono.fromFuture(s3Client.putObject(requestBody, content)));
    }

    @NotNull
    private static Mono<PutObjectRequest> preparePutObjectRequest(@NotNull FileUploadTarget uploadTarget,
                                                                  @NotNull String s3FileKey,
                                                                  @NotNull String bucketName) {
        /*
        Spring does not provide content-length for single part
        It loads the file in the memory, that can lead to out-of memory or memory leak.
        Maybe there is another way to do this?
        */
        Flux<DataBuffer> content = uploadTarget.getFilePart().content();

        return content.map(DataBuffer::readableByteCount)
                .reduce(Integer::sum)
                .log()
                .map(contentLength -> PutObjectRequest.builder()
                        .key(s3FileKey)
                        .bucket(bucketName)
                        .contentLength((long) contentLength)
                        .contentType(uploadTarget.getFilePart().headers().getContentType().toString())
                        .build());
    }

    @NotNull
    private static AsyncRequestBody prepareContent(FileUploadTarget uploadTarget) {
        return fromPublisher(uploadTarget.getFilePart().content()
                .flatMapSequential(dataBuffer -> Flux.fromIterable(dataBuffer::readableByteBuffers)));
    }
}
