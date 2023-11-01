package com.odeyalo.sonata.harmony.support.utils;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class FilenameUtilsTest {

    @ParameterizedTest
    @ValueSource(strings = {"miku.png", "miku.jpeg", "miku,jpg", "miku.gif", "miku.webp"})
    void shouldReturnTrueForImageFormats(String filename) {
        assertThat(FilenameUtils.isImageFile(filename)).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"miku.mp3", "miku.pdf", "miku,txt", "miku.mp4", "miku.apk"})
    void shouldReturnFalseForNonImageFormats(String filename) {
        assertThat(FilenameUtils.isImageFile(filename)).isFalse();
    }
}