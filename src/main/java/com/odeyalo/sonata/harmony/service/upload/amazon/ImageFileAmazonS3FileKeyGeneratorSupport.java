package com.odeyalo.sonata.harmony.service.upload.amazon;

import com.odeyalo.sonata.harmony.service.upload.FileUploadTarget;
import com.odeyalo.sonata.harmony.support.utils.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import reactor.core.publisher.Mono;

/**
 * Support only image files and generate keys for them only
 */
public class ImageFileAmazonS3FileKeyGeneratorSupport implements AmazonS3FileKeyGeneratorSupport<FileUploadTarget> {
    public static final String IMAGE_FILE_S3_PREFIX = "i/";

    @Override
    @NotNull
    public Mono<String> generateFileKey(@NotNull FileUploadTarget fileUploadTarget) {
        String filename = fileUploadTarget.getFilePart().filename();

        if ( FilenameUtils.isImageFile(filename) ) {
            return Mono.just(generateFileKey());
        }

        return Mono.empty();
    }

    @NotNull
    private static String generateFileKey() {
        String fileId = RandomStringUtils.randomAlphabetic(50);
        return StringUtils.join(IMAGE_FILE_S3_PREFIX, fileId);
    }
}
