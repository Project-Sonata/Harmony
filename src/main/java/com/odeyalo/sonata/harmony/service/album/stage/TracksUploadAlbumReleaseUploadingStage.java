package com.odeyalo.sonata.harmony.service.album.stage;

import com.odeyalo.sonata.harmony.entity.*;
import com.odeyalo.sonata.harmony.entity.AlbumReleaseEntity.AlbumReleaseEntityBuilder;
import com.odeyalo.sonata.harmony.model.Artist;
import com.odeyalo.sonata.harmony.service.album.TrackUploadTarget;
import com.odeyalo.sonata.harmony.service.album.UploadAlbumReleaseInfo;
import com.odeyalo.sonata.harmony.service.album.support.AlbumTracksUploader;
import com.odeyalo.sonata.harmony.service.upload.FileUploadTarget;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class TracksUploadAlbumReleaseUploadingStage implements AlbumReleaseUploadingStage {
    private final AlbumTracksUploader albumTracksUploader;

    public TracksUploadAlbumReleaseUploadingStage(AlbumTracksUploader albumTracksUploader) {
        this.albumTracksUploader = albumTracksUploader;
    }

    @Override
    @NotNull
    public Mono<AlbumReleaseEntityBuilder<?, ?>> processAlbumUpload(@NotNull UploadAlbumReleaseInfo info,
                                                                    @NotNull AlbumReleaseEntityBuilder<?, ?> prevProcessed,
                                                                    @NotNull Flux<FilePart> tracks,
                                                                    @NotNull Mono<FilePart> coverImage) {

        Flux<FileUploadTarget> targets = tracks.map(f -> FileUploadTarget.of(f.filename(), f));
        ArtistContainerEntity artists = toArtistContainer(info);

        return albumTracksUploader.uploadTracks(targets)
                .map(uploadedFile -> {
                    TrackUploadTarget target = info.getTracks().findByTrackFileId(uploadedFile.getFileId()).get();
                    return toSimplifiedTrack(artists, target, uploadedFile.getFileUrl().getUrl());
                })
                .collectList()
                .map(TrackContainerEntity::wrap)
                .map(prevProcessed::tracks);
    }

    private static ArtistContainerEntity toArtistContainer(@NotNull UploadAlbumReleaseInfo info) {
        List<ArtistEntity> artists = info.getArtists().stream()
                .map(TracksUploadAlbumReleaseUploadingStage::convertToArtistEntity)
                .toList();

        return ArtistContainerEntity.multiple(artists);
    }

    private static ArtistEntity convertToArtistEntity(Artist artist) {
        return ArtistEntity.builder().sonataId(artist.getSonataId())
                .name(artist.getName())
                .build();
    }


    // TODO: refactor me
    private static SimplifiedTrackEntity toSimplifiedTrack(ArtistContainerEntity artists, TrackUploadTarget trackUploadTarget, String url) {
        return SimplifiedTrackEntity.builder()
                .name(trackUploadTarget.getTrackName())
                .artists(artists)
                .durationMs(trackUploadTarget.getDurationMs())
                .discNumber(trackUploadTarget.getDiscNumber())
                .index(trackUploadTarget.getIndex())
                .explicit(trackUploadTarget.isExplicit())
                .hasLyrics(trackUploadTarget.hasLyrics())
                .trackUrl(url)
                .build();
    }
}
