package com.odeyalo.sonata.harmony.api.rest;

import com.odeyalo.sonata.harmony.dto.*;
import com.odeyalo.sonata.harmony.model.AlbumType;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import testing.faker.TrackDtoFaker;

import static com.odeyalo.sonata.harmony.dto.ReleaseArtistContainerDto.solo;
import static com.odeyalo.sonata.harmony.dto.TrackContainerDto.single;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
@AutoConfigureWebTestClient
@ActiveProfiles("test")
public class UploadAlbumReleaseEndpointTest {

    public static final String TRACK_1_PATH = "./music/test.mp3";
    @Autowired
    WebTestClient webTestClient;

    static final String ALBUM_COVER_PATH = "./img/album-cover.png";

    @Nested
    @TestInstance(Lifecycle.PER_CLASS)
    class ValidRequestTests {

        @Test
        void shouldReturnAcceptedStatus() {
            WebTestClient.ResponseSpec responseSpec = sendRequestWithValidData();

            responseSpec.expectStatus().isAccepted();
        }

        @Test
        void shouldReturnTrackingIdInBody() {
            WebTestClient.ResponseSpec responseSpec = sendRequestWithValidData();

            var responseBody = responseSpec.expectBody(AlbumReleaseUploadAcceptedResponse.class).returnResult().getResponseBody();

            assertThat(responseBody).isNotNull();

            String trackingId = responseBody.getTrackingId();

            webTestClient.get().uri("/tracking/{trackingId}", trackingId)
                    .exchange()
                    .expectStatus().isOk();
        }

        @NotNull
        private WebTestClient.ResponseSpec sendRequestWithValidData() {
            MultipartBodyBuilder builder = prepareValidRequestBody();

            return sendUploadAlbumRelease(builder);
        }

        @NotNull
        private static MultipartBodyBuilder prepareValidRequestBody() {
            UploadAlbumReleaseRequest requestBody = prepareValidCustomizableRequestBody().build();

            return prepareRequest(requestBody);
        }
    }

    @Nested
    @TestInstance(Lifecycle.PER_CLASS)
    class InvalidRequestBodyPayloadTests {

        @Test
        void shouldReturnBadRequestIfNameIsNull() {
            UploadAlbumReleaseRequest requestBody = prepareValidCustomizableRequestBody()
                    .albumName(null).build();

            MultipartBodyBuilder builder = prepareRequest(requestBody);

            WebTestClient.ResponseSpec responseSpec = sendUploadAlbumRelease(builder);

            responseSpec.expectStatus().isBadRequest();
        }

        @Test
        void shouldReturnBadRequestIfAlbumTypeIsNull() {
            UploadAlbumReleaseRequest requestBody = prepareValidCustomizableRequestBody()
                    .albumType(null).build();

            MultipartBodyBuilder builder = prepareRequest(requestBody);

            WebTestClient.ResponseSpec responseSpec = sendUploadAlbumRelease(builder);

            responseSpec.expectStatus().isBadRequest();
        }

        @Test
        void shouldReturnBadRequestIfPerformersAreNull() {
            UploadAlbumReleaseRequest requestBody = prepareValidCustomizableRequestBody()
                    .performers(null).build();

            MultipartBodyBuilder builder = prepareRequest(requestBody);

            WebTestClient.ResponseSpec responseSpec = sendUploadAlbumRelease(builder);

            responseSpec.expectStatus().isBadRequest();
        }

        @Test
        void shouldReturnBadRequestIfTracksAreNull() {
            UploadAlbumReleaseRequest requestBody = prepareValidCustomizableRequestBody()
                    .tracks(null).build();

            MultipartBodyBuilder builder = prepareRequest(requestBody);

            WebTestClient.ResponseSpec responseSpec = sendUploadAlbumRelease(builder);

            responseSpec.expectStatus().isBadRequest();
        }
    }

    private static UploadAlbumReleaseRequest.UploadAlbumReleaseRequestBuilder prepareValidCustomizableRequestBody() {
        ReleaseArtistContainerDto artistContainer = solo(ReleaseArtistDto.of("123"));
        TrackDto track = TrackDtoFaker.create().get();

        TrackContainerDto container = single(track);

        return UploadAlbumReleaseRequest.builder()
                .albumName("something")
                .albumType(AlbumType.SINGLE)
                .performers(artistContainer)
                .tracks(container);
    }

    @NotNull
    private static MultipartBodyBuilder prepareRequest(UploadAlbumReleaseRequest requestBody) {
        MultipartBodyBuilder builder = new MultipartBodyBuilder();

        builder.part("body", requestBody, MediaType.APPLICATION_JSON);

        ClassPathResource albumCover = new ClassPathResource(ALBUM_COVER_PATH);

        ClassPathResource track1 = new ClassPathResource(TRACK_1_PATH);

        builder.part("cover", BodyInserters.fromResource(albumCover))
                .filename("hoshino.png");

        builder.part("tracks", BodyInserters.fromResource(track1))
                .filename("test.mp3");

        return builder;
    }

    @NotNull
    private WebTestClient.ResponseSpec sendUploadAlbumRelease(MultipartBodyBuilder builder) {
        return webTestClient.post()
                .uri("/release/upload/album")
                .body(BodyInserters.fromMultipartData(builder.build()))
                .exchange();
    }
}