package com.odeyalo.sonata.harmony.service.upload.amazon;

import com.odeyalo.sonata.harmony.service.upload.FileUrl;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PrefixedUrlAmazonS3FileUrlResolverTest {


    @Test
    void providePrefixWithoutSlashAndExpectSlashToBeAdded() {
        String prefix = "https://cdn.sonata.com";
        PrefixedUrlAmazonS3FileUrlResolver testable = new PrefixedUrlAmazonS3FileUrlResolver(prefix);

        FileUrl fileUrl = testable.resolveUrl("imageid").block();

        assertThat(fileUrl).isNotNull();
        assertThat(fileUrl.getUrl()).isEqualTo(prefix + "/" + "imageid");
    }

    @Test
    void providePrefixWithSlashAndExpectValidUrlToBeReturned() {
        String prefix = "https://cdn.sonata.com/";
        PrefixedUrlAmazonS3FileUrlResolver testable = new PrefixedUrlAmazonS3FileUrlResolver(prefix);

        FileUrl fileUrl = testable.resolveUrl("imageid").block();

        assertThat(fileUrl).isNotNull();
        assertThat(fileUrl.getUrl()).isEqualTo(prefix + "imageid");
    }

    @Test
    void provideNullPrefixAndExpectException() {
        assertThatThrownBy(() -> new PrefixedUrlAmazonS3FileUrlResolver(null))
                .isInstanceOf(IllegalArgumentException.class);
    }
    @Test
    void provideInvalidUrlAsPrefixAndExpectException() {
        assertThatThrownBy(() -> new PrefixedUrlAmazonS3FileUrlResolver("null"))
                .isInstanceOf(IllegalStateException.class);
    }
}