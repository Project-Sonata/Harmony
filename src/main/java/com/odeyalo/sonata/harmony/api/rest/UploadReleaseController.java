package com.odeyalo.sonata.harmony.api.rest;

import com.odeyalo.sonata.harmony.dto.UploadAlbumReleaseRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/release/upload")
public class UploadReleaseController {

    @PostMapping("/album")
    public Mono<ResponseEntity<Object>> uploadAlbum(
            @RequestPart("body") UploadAlbumReleaseRequest body,
            @RequestPart("cover") Mono<FilePart> albumCover
    ) {
        return albumCover
                .map(t -> ResponseEntity.accepted().build())
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }
}
