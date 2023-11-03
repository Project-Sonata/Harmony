package com.odeyalo.sonata.harmony.service.album.support;

import com.odeyalo.sonata.harmony.service.upload.FileUploadTarget;
import com.odeyalo.sonata.harmony.service.upload.FileUrl;
import com.odeyalo.sonata.harmony.service.upload.UploadedFile;
import reactor.core.publisher.Flux;

public class MockAlbumTracksUploader implements AlbumTracksUploader {
    private final FileUrl fileUrl;

    public MockAlbumTracksUploader(FileUrl fileUrl) {
        this.fileUrl = fileUrl;
    }

    public MockAlbumTracksUploader(String savedTrackUrl) {
        this.fileUrl = FileUrl.urlOnly(savedTrackUrl);
    }

    @Override
    public Flux<UploadedFile> uploadTracks(Flux<FileUploadTarget> tracks) {
        return tracks.map(target -> UploadedFile.of(target.getId(), fileUrl));
    }
}
