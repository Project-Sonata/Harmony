package com.odeyalo.sonata.harmony.service.album.stage;

import com.odeyalo.sonata.harmony.entity.AlbumCoverImageContainerEntity;
import com.odeyalo.sonata.harmony.entity.AlbumCoverImageEntity;
import com.odeyalo.sonata.harmony.entity.AlbumReleaseEntity.AlbumReleaseEntityBuilder;
import com.odeyalo.sonata.harmony.model.Image;
import com.odeyalo.sonata.harmony.service.album.UploadAlbumReleaseInfo;
import com.odeyalo.sonata.harmony.service.album.support.AlbumCoverImageUploader;
import com.odeyalo.sonata.harmony.service.upload.FileUploadTarget;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class UploadCoverImageAlbumReleaseUploadingStage implements AlbumReleaseUploadingStage {
    private final AlbumCoverImageUploader albumCoverImageUploader;

    public UploadCoverImageAlbumReleaseUploadingStage(AlbumCoverImageUploader albumCoverImageUploader) {
        this.albumCoverImageUploader = albumCoverImageUploader;
    }

    @Override
    @NotNull
    public Mono<AlbumReleaseEntityBuilder<?, ?>> processAlbumUpload(@NotNull UploadAlbumReleaseInfo info,
                                                                    @NotNull AlbumReleaseEntityBuilder<?, ?> prevProcessed,
                                                                    @NotNull Flux<FilePart> tracks,
                                                                    @NotNull Mono<FilePart> coverImage) {

        Mono<FileUploadTarget> targets = coverImage.map(f -> FileUploadTarget.of(f.filename(), f));

        return albumCoverImageUploader.uploadAlbumCoverImage(targets)
                .map(image -> prevProcessed.images(toImages(image)));
    }

    private static AlbumCoverImageContainerEntity toImages(Image image) {
        return AlbumCoverImageContainerEntity.single(
                AlbumCoverImageEntity.builder()
                        .height(image.getHeight())
                        .width(image.getWidth())
                        .url(image.getUrl())
                        .build()
        );
    }
}
