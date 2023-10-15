package com.odeyalo.sonata.harmony.service;

import com.odeyalo.sonata.harmony.config.AlbumReleaseUploadConfiguration;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AlbumReleaseUploaderTest {

    AlbumReleaseUploader albumReleaseUploader = new AlbumReleaseUploadConfiguration().albumReleaseUploader();
}
