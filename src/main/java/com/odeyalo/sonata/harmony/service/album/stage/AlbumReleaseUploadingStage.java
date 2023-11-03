package com.odeyalo.sonata.harmony.service.album.stage;

import com.odeyalo.sonata.harmony.entity.AlbumReleaseEntity.AlbumReleaseEntityBuilder;
import com.odeyalo.sonata.harmony.service.album.UploadAlbumReleaseInfo;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AlbumReleaseUploadingStage {

    @NotNull
    Mono<AlbumReleaseEntityBuilder<?, ?>> processAlbumUpload(@NotNull UploadAlbumReleaseInfo info,
                                                             @NotNull AlbumReleaseEntityBuilder<?, ?> prevProcessed,
                                                             @NotNull Flux<FilePart> tracks,
                                                             @NotNull Mono<FilePart> coverImage);
}
