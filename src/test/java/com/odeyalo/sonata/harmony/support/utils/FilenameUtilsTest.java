package com.odeyalo.sonata.harmony.support.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FilenameUtilsTest {

    // Image file types tests

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

    // Music file types tests

    @Test
    void shouldThrowExceptionIfNullIsProvided() {
        assertThatThrownBy(() -> FilenameUtils.isMusicFile(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"track.mp3", "track.ogg", "track.wav"})
    void shouldReturnTrueForTrackFormats(String filename) {
        assertThat(FilenameUtils.isMusicFile(filename)).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"miku.pdf", "miku,txt", "miku.mp4", "miku.apk"})
    void shouldReturnFalseForNonTrackFormats(String filename) {
        assertThat(FilenameUtils.isImageFile(filename)).isFalse();
    }
}