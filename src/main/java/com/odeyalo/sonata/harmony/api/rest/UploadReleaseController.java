package com.odeyalo.sonata.harmony.api.rest;

import com.odeyalo.sonata.harmony.dto.AlbumReleaseUploadAcceptedResponse;
import com.odeyalo.sonata.harmony.dto.UploadAlbumReleaseRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.odeyalo.sonata.harmony.support.http.HttpStatuses.accepted;
import static com.odeyalo.sonata.harmony.support.http.HttpStatuses.badRequest;

@RestController
@RequestMapping("/release/upload")
public class UploadReleaseController {

    @PostMapping("/album")
    public Mono<ResponseEntity<AlbumReleaseUploadAcceptedResponse>> uploadAlbum(
            @Validated @RequestPart("body") UploadAlbumReleaseRequest body,
            @RequestPart("tracks") Flux<FilePart> tracks,
            @RequestPart("cover") Mono<FilePart> albumCover
    ) {
        return albumCover
                .map(t -> accepted(
                        AlbumReleaseUploadAcceptedResponse.of("uniqueid")
                ))
                .defaultIfEmpty(badRequest());
    }
}
