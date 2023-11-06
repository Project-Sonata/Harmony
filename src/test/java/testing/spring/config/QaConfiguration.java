package testing.spring.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.web.reactive.server.WebTestClient;
import testing.qa.QaOperations;
import testing.qa.WebTestClientQaOperations;

@TestConfiguration
public class QaConfiguration {

    @Bean
    public QaOperations qaOperations(WebTestClient webTestClient) {
        return new WebTestClientQaOperations(webTestClient);
    }
}
