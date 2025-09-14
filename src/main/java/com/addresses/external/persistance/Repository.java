package com.addresses.external.persistance;

import com.addresses.external.persistance.dto.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface Repository extends ReactiveMongoRepository<User, String> {

    Mono<User> findByCpf(String cpf);
}
