package com.addresses.external;

import com.addresses.core.model.Address;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class OutsideAPI {

    private static final Logger log = LoggerFactory.getLogger(OutsideAPI.class);
    WebClient webClient = WebClient.create();

    public Mono<Address> getAddress(String zipCode) {
        return webClient.get()
                .uri("https://brasilapi.com.br/api/cep/v1/" + zipCode)
                .exchangeToMono(clientResponse -> clientResponse.bodyToMono(Address.class));
    }
}