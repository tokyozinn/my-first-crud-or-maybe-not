package com.addresses.external;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class SensitiveDataAPI {

    WebClient webClient = WebClient.create();

    public Mono<String> getSensitiveData(String cpf) {
        return webClient.get()
                .uri("http://localhost:8081/sensitive")
                .retrieve()
                .bodyToMono(String.class);
    }
}
