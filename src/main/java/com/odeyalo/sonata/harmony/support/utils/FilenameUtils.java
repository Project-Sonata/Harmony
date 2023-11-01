package com.odeyalo.sonata.harmony.support.utils;

import org.springframework.util.Assert;

import java.util.List;

public class FilenameUtils {

    private static final List<String> IMAGE_EXTENSIONS = List.of(
            "png", "jpeg", "jpg", "gif", "webp"
    );

    public static boolean isImageFile(String filename) {
        Assert.notNull(filename, "Filename must be not null!");
        return IMAGE_EXTENSIONS.stream().anyMatch(filename::endsWith);
    }
}
