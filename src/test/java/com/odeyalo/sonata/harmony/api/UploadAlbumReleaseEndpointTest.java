package com.odeyalo.sonata.harmony.api;

import com.odeyalo.sonata.harmony.dto.UploadAlbumReleaseRequest;
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

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
@AutoConfigureWebTestClient
@ActiveProfiles("test")
public class UploadAlbumReleaseEndpointTest {

    @Autowired
    WebTestClient webTestClient;

    static final String ALBUM_COVER_PATH = "./img/album-cover.png";

    @Nested
    @TestInstance(Lifecycle.PER_CLASS)
    class ValidRequestTests {

        @Test
        void shouldReturnAcceptedStatus() {
            MultipartBodyBuilder builder = prepareValidRequestBody();

            WebTestClient.ResponseSpec responseSpec = sendUploadAlbumRelease(builder);

            responseSpec.expectStatus().isAccepted();
        }

        @NotNull
        private static MultipartBodyBuilder prepareValidRequestBody() {
            MultipartBodyBuilder builder = new MultipartBodyBuilder();

            UploadAlbumReleaseRequest requestBody = UploadAlbumReleaseRequest.builder().build();

            builder.part("body", requestBody, MediaType.APPLICATION_JSON);

            ClassPathResource albumCover = new ClassPathResource(ALBUM_COVER_PATH);

            builder.part("cover", BodyInserters.fromResource(albumCover))
                    .filename("hoshino.png");

            return builder;
        }
    }

    @NotNull
    private WebTestClient.ResponseSpec sendUploadAlbumRelease(MultipartBodyBuilder builder) {
        return webTestClient.post()
                .uri("/release/upload/album")
                .body(BodyInserters.fromMultipartData(builder.build()))
                .exchange();
    }
}
