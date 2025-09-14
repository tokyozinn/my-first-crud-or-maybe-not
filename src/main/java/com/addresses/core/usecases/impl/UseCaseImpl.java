package com.addresses.core.usecases.impl;

import com.addresses.core.usecases.UseCase;
import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Data
@Builder
public class UseCaseImpl implements UseCase {

    @Override
    public Mono<Boolean> verify(String cpf) {
        return Mono.just(cpf.contains("00"));
    }
}
