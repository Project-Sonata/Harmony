package com.odeyalo.sonata.harmony.api.rest;

import com.odeyalo.sonata.harmony.dto.AlbumReleaseUploadAcceptedResponse;
import com.odeyalo.sonata.harmony.dto.UploadAlbumReleaseRequest;
import com.odeyalo.sonata.harmony.service.album.UploadAlbumReleaseInfo;
import com.odeyalo.sonata.harmony.service.album.tracking.TrackableAlbumReleaseUploaderFacade;
import com.odeyalo.sonata.harmony.support.converter.UploadAlbumReleaseInfoConverter;
import com.odeyalo.sonata.harmony.support.http.HttpStatuses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.odeyalo.sonata.harmony.support.http.HttpStatuses.accepted;

@RestController
@RequestMapping("/release/upload")
public class UploadReleaseController {
    private final TrackableAlbumReleaseUploaderFacade albumReleaseUploaderFacade;

    @Autowired
    UploadAlbumReleaseInfoConverter releaseInfoConverter;

    public UploadReleaseController(TrackableAlbumReleaseUploaderFacade albumReleaseUploaderFacade) {
        this.albumReleaseUploaderFacade = albumReleaseUploaderFacade;
    }

    @PostMapping("/test")
    public Mono<ResponseEntity<?>> test(@RequestBody UploadAlbumReleaseRequest body) {

        return Mono.just(ResponseEntity.ok(body));
    }

    @PostMapping("/album")
    public Mono<ResponseEntity<AlbumReleaseUploadAcceptedResponse>> uploadAlbum(
            @Validated @RequestPart("body") UploadAlbumReleaseRequest body,
            @RequestPart("tracks") Flux<FilePart> tracks,
            @RequestPart("cover") Mono<FilePart> albumCover
    ) {


        UploadAlbumReleaseInfo releaseInfo = releaseInfoConverter.toUploadAlbumReleaseInfo(body);

        return albumReleaseUploaderFacade.uploadAlbumRelease(releaseInfo, tracks, albumCover)
                .map(trackingInfo -> accepted(AlbumReleaseUploadAcceptedResponse.of(trackingInfo.getTrackingId())))
                .defaultIfEmpty(HttpStatuses.badRequest());
    }
}
