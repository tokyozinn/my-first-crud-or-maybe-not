package com.addresses.core.usecases;

import reactor.core.publisher.Mono;

public interface UseCase {

    Mono<Boolean> verify(String cpf);
}
