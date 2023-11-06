package testing.qa;

import org.springframework.test.web.reactive.server.WebTestClient;

public class WebTestClientQaOperations implements QaOperations {
    private final WebTestClient webTestClient;

    public WebTestClientQaOperations(WebTestClient webTestClient) {
        this.webTestClient = webTestClient;
    }

    @Override
    public void clearEverything() {
        webTestClient.delete().uri("/qa/clear").exchange()
                .expectStatus().isOk();
    }
}
