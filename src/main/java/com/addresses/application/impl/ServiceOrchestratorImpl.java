package com.addresses.application.impl;

import com.addresses.application.ServiceOrchestrator;
import com.addresses.core.model.Address;
import com.addresses.core.usecases.UseCase;
import com.addresses.external.OutsideAPI;
import com.addresses.external.Request;
import com.addresses.external.SensitiveDataAPI;
import com.addresses.external.persistance.Repository;
import com.addresses.external.persistance.dto.User;
import lombok.Builder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
@Builder
public class ServiceOrchestratorImpl implements ServiceOrchestrator {
    private static final Logger log = LoggerFactory.getLogger(ServiceOrchestratorImpl.class);
    private final Repository repository;
    private final SensitiveDataAPI sensitiveDataAPI;
    private final OutsideAPI outsideAPI;
    private final UseCase useCase;

    @Override
    public Mono<Address> execute(Request request) {
        User user = User.builder().cpf(request.getCpf()).email(request.getEmail()).build();
        return outsideAPI.getAddress(request.getZipCode())
                .flatMap(it -> useCase.verify(request.getCpf())
                        .filter(bool -> bool)
                        .flatMap(b -> sensitiveDataAPI.getSensitiveData(request.getCpf()))
                        .flatMap(a -> Mono.just(Address.builder()
                                .city(it.getCity())
                                .state(it.getState())
                                .street(it.getStreet())
                                .neighborhood(it.getNeighborhood())
                                .build()))
                        .switchIfEmpty(Mono.just(Address.builder()
                                        .city(it.getCity())
                                        .state(it.getState())
                                .build())))
                .doFirst(() ->
                        repository.save(user)
                                .subscribeOn(Schedulers.boundedElastic())
                                .doOnSuccess(s -> log.info(String.format("Saved user %s", user)))
                                .doOnError(err -> log.info(String.format("Error %s", err.getMessage())))
                                .onErrorResume(err -> Mono.empty())
                                .then()
                                .subscribe());
    }
}
