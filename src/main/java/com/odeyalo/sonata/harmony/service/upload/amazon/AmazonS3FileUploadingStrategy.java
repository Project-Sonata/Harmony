package com.odeyalo.sonata.harmony.service.upload.amazon;

import com.odeyalo.sonata.harmony.service.upload.FileUploadTarget;
import org.jetbrains.annotations.NotNull;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

/**
 * Strategy to upload the file to the Amazon S3 storage.
 */
public interface AmazonS3FileUploadingStrategy {

    Mono<UploadResult> uploadFile(FileUploadTarget uploadTarget);

    /**
     * Represent the upload result by Amazon S3 Service.
     *
     * @param fileId         - Original id of the file that was uploaded to S3. Equal to {@link FileUploadTarget#getId()}
     * @param s3FileKey      - key(id) of the file that stored in S3. Not forced to be equal to File ID.
     * @param amazonResponse - response from the Amazon S3 Service.
     */
    record UploadResult(@NotNull String fileId,
                        @NotNull String s3FileKey,
                        @NotNull PutObjectResponse amazonResponse) {

        public static UploadResult of(@NotNull String fileId,
                                      @NotNull String s3FileKey,
                                      @NotNull PutObjectResponse amazonResponse) {
            return new UploadResult(fileId, s3FileKey, amazonResponse);
        }
    }
}
