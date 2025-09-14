package com.addresses.external;

import com.addresses.core.model.Address;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class OutsideAPI {

    WebClient webClient = WebClient.create();

    Mono<Address> getAddress(String zipCode) {
        return webClient.get()
                .uri("https://brasilapi.com.br/api/cep/v1/" + zipCode)
                .retrieve()
                .bodyToMono(Address.class);
    }
}