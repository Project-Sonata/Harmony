package com.odeyalo.sonata.harmony.support.utils;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Utils to work with URL
 */
public class UrlUtils {

    public static boolean isValid(String url) {
        try {
            new URL(url).toURI();
            return true;
        } catch (MalformedURLException | URISyntaxException e) {
            return false;
        }
    }
}
