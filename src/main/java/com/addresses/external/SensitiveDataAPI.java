package com.addresses.external;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class SensitiveDataAPI {

    WebClient webClient = WebClient.create();

    public Mono<String> getSensitiveData(String cpf) {
        return webClient.get()
                .uri("http://localhost:9090/sensitive")
                .retrieve()
                .bodyToMono(String.class);
    }
}
