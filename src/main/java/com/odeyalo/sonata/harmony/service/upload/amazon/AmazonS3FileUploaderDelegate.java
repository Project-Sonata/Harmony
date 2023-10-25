package com.odeyalo.sonata.harmony.service.upload.amazon;

import com.odeyalo.sonata.harmony.service.upload.FileUploadTarget;
import com.odeyalo.sonata.harmony.service.upload.FileUploaderDelegate;
import com.odeyalo.sonata.harmony.service.upload.FileUrl;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * Upload the file to amazon, resolve its URL and then just return it
 */
@Component
public class AmazonS3FileUploaderDelegate implements FileUploaderDelegate {
    private final AmazonS3FileUploadingStrategy uploadingStrategy;
    private final AmazonS3FileUrlResolver amazonS3FileUrlResolver;

    @Autowired
    public AmazonS3FileUploaderDelegate(AmazonS3FileUploadingStrategy uploadingStrategy,
                                        AmazonS3FileUrlResolver amazonS3FileUrlResolver) {
        this.uploadingStrategy = uploadingStrategy;
        this.amazonS3FileUrlResolver = amazonS3FileUrlResolver;
    }

    @Override
    @NotNull
    public Mono<FileUrl> uploadFile(@NotNull FileUploadTarget file) {
        return uploadingStrategy.uploadFile(file)
                .flatMap(uploadResult -> amazonS3FileUrlResolver.resolveUrl(uploadResult.s3FileKey()));
    }
}
