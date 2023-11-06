package com.odeyalo.sonata.harmony.api.rest;

import com.odeyalo.sonata.harmony.repository.AlbumReleaseTrackingRepository;
import com.odeyalo.sonata.harmony.service.album.tracking.AlbumReleaseUploadTrackingService;
import com.odeyalo.sonata.harmony.support.http.HttpStatuses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/tracking")
public class ReleaseUploadTrackingController {
    private final AlbumReleaseUploadTrackingService trackingService;

    @Autowired
    public ReleaseUploadTrackingController(AlbumReleaseUploadTrackingService trackingService) {
        this.trackingService = trackingService;
    }

    @GetMapping("/{trackingId}")
    public Mono<ResponseEntity<Object>> getCurrentStatus(@PathVariable String trackingId) {
        return trackingService.findByTrackingId(trackingId)
                .map(res -> HttpStatuses.ok())
                .defaultIfEmpty(HttpStatuses.unprocessableEntity());
    }
}
