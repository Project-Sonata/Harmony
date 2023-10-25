package com.odeyalo.sonata.harmony.service.upload;

import com.odeyalo.sonata.harmony.support.utils.UrlUtils;
import lombok.Value;
import org.springframework.util.Assert;

/**
 * Immutable wrapper class for URL for the file.
 * Url is wrapped to check its validity.
 */
@Value
public class FileUrl {
    String url;

    private FileUrl(String url) {
        Assert.state(UrlUtils.isValid(url), "Url should be valid!");
        this.url = url;
    }

    public static FileUrl urlOnly(String url) {
        return new FileUrl(url);
    }
}
