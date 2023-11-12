package com.odeyalo.sonata.harmony.service.upload.amazon;

import com.odeyalo.sonata.harmony.service.upload.FileUploadTarget;
import com.odeyalo.sonata.harmony.support.utils.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import reactor.core.publisher.Mono;

/**
 * Support only music files and generate file keys for them only
 */
public class MusicFileAmazonS3FileKeyGeneratorSupport implements AmazonS3FileKeyGeneratorSupport<FileUploadTarget> {
    private static final String MUSIC_FILE_S3_PREFIX = "m/";

    @Override
    @NotNull
    public Mono<String> generateFileKey(@NotNull FileUploadTarget fileUploadTarget) {
        String filename = fileUploadTarget.getFilePart().filename();

        if ( FilenameUtils.isMusicFile(filename) ) {
            return Mono.just(generateFileKey());
        }

        return Mono.empty();
    }

    @NotNull
    private static String generateFileKey() {
        String randomId = RandomStringUtils.randomAlphanumeric(50);
        return StringUtils.join(MUSIC_FILE_S3_PREFIX, randomId);
    }
}
