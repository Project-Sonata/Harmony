package com.odeyalo.sonata.harmony.service.upload.amazon;

import com.odeyalo.sonata.harmony.service.upload.FileUploadTarget;
import com.odeyalo.sonata.harmony.service.upload.FileUploaderDelegate;
import com.odeyalo.sonata.harmony.service.upload.FileUrl;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class AmazonS3FileUploaderDelegate implements FileUploaderDelegate {
    private final AmazonS3FileUploadingStrategy uploadingStrategy;

    @Autowired
    public AmazonS3FileUploaderDelegate(AmazonS3FileUploadingStrategy uploadingStrategy) {
        this.uploadingStrategy = uploadingStrategy;
    }

    @Override
    @NotNull
    public Mono<FileUrl> uploadFile(@NotNull FileUploadTarget file) {
        return uploadingStrategy.uploadFile(file)
                .thenReturn(FileUrl.urlOnly("https://cdn.sonata.com/autogeneratedid"));
    }
}
