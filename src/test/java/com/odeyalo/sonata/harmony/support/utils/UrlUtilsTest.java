package com.odeyalo.sonata.harmony.support.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UrlUtilsTest {

    @Test
    void shouldReturnTrueForValidUrl() {
        assertTrue(UrlUtils.isValid("https://sonata.com/"));
    }

    @Test
    void shouldReturnFalseForInvalidUrl() {
        assertFalse(UrlUtils.isValid("https:///\\invalid-fs21"));
    }
}